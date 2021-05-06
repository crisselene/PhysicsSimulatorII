package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.EpsilonEqualStatesBuilder;
import simulator.factories.Factory;
import simulator.factories.MassEqualStatesBuilder;
import simulator.factories.MassLosingBodyBuilder;
import simulator.factories.MovingTowardsFixedPointBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoForceBuilder;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _modeDefaultValue = "batch";
	private final static String _stateComparatorDefaultValue = "epseq";
	private final static Double _stepsDefaultValue = 150.0;//default steps
	

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String _outFile = null;//outfile
	private static String _outFileExpected = null;//outfile expected
	private static String _mode = null; //mode
	private static int _steps = 150;//steps, 150 default
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;
	private static Factory<StateComparator> _stateComparatorFactory;

	private static void init() {
		
		//initialize the bodies factory (este codigo nos lo dan)
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);	
		
		//initialize the force laws factory(igual pero con las leyes)
		ArrayList<Builder<ForceLaws>> lawsBuilder = new ArrayList<>();
		lawsBuilder.add(new NewtonUniversalGravitationBuilder());
		lawsBuilder.add(new MovingTowardsFixedPointBuilder());
		lawsBuilder.add(new NoForceBuilder());
		_forceLawsFactory = new BuilderBasedFactory<ForceLaws>(lawsBuilder);
		
		//initialize the state comparator(y con los comparadores)
		ArrayList<Builder<StateComparator>> comparatorBuilder = new ArrayList<>();
		comparatorBuilder.add(new MassEqualStatesBuilder());
		comparatorBuilder.add(new EpsilonEqualStatesBuilder());
		_stateComparatorFactory = new BuilderBasedFactory<StateComparator>(comparatorBuilder);
		
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			//add support of -o, -eo, and -s (define corresponding parse methods)
			parseOutFileOption(line);// o , output
			parseExpectedOutput(line); //eo , expected output
			parseSteps(line); // s, steps
			parseModeOption(line); //mode
			
			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}


	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		//add support for -o, -eo, and -s (add corresponding information to
		// cmdLineOptions):
		
		// ouput file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Bodies JSON output file.").build());

		// ouputExpected file
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected output").hasArg().desc("Bodies JSON expected output file.").build());

		// steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("number of\n" + 
				"simulation steps.").build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());
		
		// mode
		//PREGUNTAR SI HAY QUE HACER ALGUNA FORMA PARA IMPRIMIR LOS DISTINTOS TIPOS DE MODO******
				cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
						.desc("Execution Mode. Possible values: ’batch’\r\n" + 
								"(Batch mode), ’gui’ (Graphical User\r\n" + 
								"Interface mode)."+ ". Default value: '"
								+ _modeDefaultValue + "'.")
						.build());

		return cmdLineOptions;
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		String s = "";

		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s = s + ", ";
			}
			s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
		}

		s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		return s;
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		String m = line.getOptionValue("m"); //tipo de modo
		if (m == null) {
			_mode= _modeDefaultValue; //por defecto es batch
		}else if(!m.equals("gui") && !m.equals("batch")){ //el modo es gui o batch
			throw new ParseException("Mode is not correct");
		}else {
			_mode = m;
		}
	}
	
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}
	}
	
	//parse outFile 
	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile =line.getOptionValue("o");
	}
	
	//parse expected output
	private static void parseExpectedOutput(CommandLine line) {
		_outFileExpected = line.getOptionValue("eo");
	}

	private static void parseSteps(CommandLine line) throws ParseException {
		//los pasos tienen que ser un numero, por tanto hacemos lo mismo
		//que en parseDeltaTimeOption	
		String steps = line.getOptionValue("s", _stepsDefaultValue.toString());
		try {
			_steps = Integer.parseInt(steps);
			assert (_steps > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid steps value: " + steps);
		}
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue); //********he cambiado a "fl" en vez de "gl"
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}
	
	

	private static void startBatchMode() throws Exception {
		InputStream is = new FileInputStream(new File(_inFile));
		
		//el fichero de salida nos lo pueden pasar o no
		//se parsea quien es el fichero de salida, si no se crea 
		OutputStream os = _outFile == null ?
				System.out : new FileOutputStream(new File(_outFile));
		
		//inicializar Physic simulator y controller
		//falta obtener la ley , con factoria tenemos que obtener una sola ley para inicializa el simulador
		PhysicsSimulator simulator = new PhysicsSimulator(_dtime, _forceLawsFactory.createInstance(_forceLawsInfo));
		Controller ctrl = new Controller(simulator, _bodyFactory, _forceLawsFactory);
		
		//output expected
		InputStream expOut = null;
		StateComparator stateCmp = null;
		if(_outFileExpected!=null) {
			expOut = new FileInputStream(new File(_outFileExpected));
			stateCmp = _stateComparatorFactory.createInstance(_stateComparatorInfo);
		}
		
		ctrl.loadBodies(is);
		
		ctrl.run(_steps, os, expOut, stateCmp);
	}
	
	private static void startGuiMode() throws Exception {
		InputStream is = _inFile == null ?
				System.in : new FileInputStream(new File(_inFile));
		
		//el fichero de salida nos lo pueden pasar o no
		//se parsea quien es el fichero de salida, si no se crea 
		
		//inicializar Physic simulator y controller
		//falta obtener la ley , con factoria tenemos que obtener una sola ley para inicializa el simulador
		PhysicsSimulator simulator = new PhysicsSimulator(_dtime, _forceLawsFactory.createInstance(_forceLawsInfo));
		Controller ctrl = new Controller(simulator, _bodyFactory, _forceLawsFactory);
		

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
			new MainWindow(ctrl); //se crea la vnetana
			ctrl.loadBodies(is); //se cargan los cuerposs
			}
			});
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if(_mode.equals("batch")) {
			startBatchMode();
		}else if(_mode.equals("gui")) {
			startGuiMode();
		}
		
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
