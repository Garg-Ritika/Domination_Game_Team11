import java.util.Scanner;

public class Main implements IConstants{
    public static void main(String [] args){

        System.out.println("Map Editor");
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Type x to exit");
            String input = scanner.nextLine();
            System.out.println("input: " + input);
            if ("x".equalsIgnoreCase(input)){
                break;
            }else{
                processCommands(input);
            }
        }

        System.out.println("Args length"+ args.length);
        for (int i = 0; i< args.length; i++){
            System.out.println(args[i]);
        }
    }

    private static void processCommands(String input){
        if (input.length() > 0 ){
            String[] commandArray = input.trim().split(" ");
            process(commandArray);
        }
    }

    private static void process(String[] args){
        if(args.length > 0){
            String firstCommand = args[0].toLowerCase();
            System.out.println("firstCommand : " + firstCommand);

            switch (firstCommand){
                case COMMAND_EDIT_CONTINENT:
                    System.out.println("command edit continent");
                    //TODO: expect -add and -remove
                    break;

                case COMMAND_EDIT_COUNTRY:
                    System.out.println("command edit country");
                    //TODO: expect -add and -remove
                    break;

                case COMMAND_EDIT_NEIGHBOUR:
                    System.out.println("command edit neighbor");
                    // TODO:expect -add and -remove
                    break;

                case COMMAND_SHOW_MAP:
                    System.out.println("command show com.riskgame.map");
                    // TODO: Display the com.riskgame.map as text
                    break;

                case COMMAND_SAVE_MAP:
                    // TODO: expect filename
                    break;

                case COMMAND_EDIT_MAP:
                    // TODO : expect filename
                    break;

                case COMMAND_VALIDATE_MAP:
                    // TODO: validate mapfile
                    break;
            }
        }
    }
}