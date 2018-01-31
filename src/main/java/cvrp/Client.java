package cvrp;

import com.fasterxml.jackson.databind.ObjectMapper;
import cvrp.problem.CVRPProblem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Client {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        List<Argument> arguments = getArguments(args);
        TaskProperties.Builder properties = TaskProperties.builder();

        String file = null;
        int port = 8001;
        String host = "localhost";

        for (Argument arg : arguments) {
            switch (arg.getName()) {
                case "f":
                    //"Instances\\A\\A-n45-k6.vrp"
                    file = arg.getValue();
                    break;
                case "rate": {
                    int val = Integer.valueOf(arg.getValue());
                    properties.withResultRefreshRate(val);
                    break;
                }
                case "cycles": {
                    int val = Integer.valueOf(arg.getValue());
                    properties.withEvolutionCyclesLimit(val);
                    break;
                }
                case "init_size": {
                    int val = Integer.valueOf(arg.getValue());
                    properties.withInitPopulationSize(val);
                    break;
                }
                case "limit": {
                    int val = Integer.valueOf(arg.getValue());
                    properties.withPopulationLimit(val);
                    break;
                }
                case "elitism": {
                    double val = Double.valueOf(arg.getValue());
                    properties.withElitismRate(val);
                    break;
                }
                case "pmx_rate": {
                    double val = Double.valueOf(arg.getValue());
                    properties.withCrossoverRate(val);
                    break;
                }
                case "swap_rate": {
                    double val = Double.valueOf(arg.getValue());
                    properties.withSwapMutationRate(val);
                    break;
                }
                case "insert_rate": {
                    double val = Double.valueOf(arg.getValue());
                    properties.withInsertMutationRate(val);
                    break;
                }
                case "shuffle_rate": {
                    double val = Double.valueOf(arg.getValue());
                    properties.withShuffleMutationRate(val);
                    break;
                }
                case "p":
                    port = Integer.valueOf(arg.getValue());
                    break;
                case "h":
                    host = arg.getValue();
                    break;
            }
        }
        if (file == null) {
            System.out.println("CVRP problem file must provided: -f \"filename.vrp\"");
            System.exit(65);
        }

        CVRPProblem problem = new CVRPProblem(file);
        Socket clientSocket = new Socket(host, port);
        String serializedTask = objectMapper.writeValueAsString(new Task(problem, properties.build()));

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(serializedTask + '\n');

        String serverResponse;
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while ((serverResponse = inFromServer.readLine()) != null) {
            System.out.println(serverResponse);
        }

        clientSocket.close();
    }

    private static List<Argument> getArguments(String[] args) {
        if (args.length % 2 != 0) {
            System.exit(64);
        }
        List<Argument> arguments = new LinkedList<>();
        for (int i = 0; i < args.length; i = i + 2) {
            String name = args[i];
            arguments.add(new Argument(args[i].substring(1, name.length()), args[i+1]));
        }

        return arguments;
    }

    private static final class Argument {
        private final String name;
        private final String value;

        private Argument(String name, String value) {
            this.name = name;
            this.value = value;
        }

        private String getName() {
            return name;
        }

        private String getValue() {
            return value;
        }
    }
}
