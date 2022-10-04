package prog02;

import java.util.Objects;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 *
 * @author vjm
 */
public class Main {

    /**
     * Processes user's commands on a phone directory.
     *
     * @param fn The file containing the phone directory.
     * @param ui The UserInterface object to use
     *           to talk to the user.
     * @param pd The PhoneDirectory object to use
     *           to process the phone directory.
     */
    public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
        pd.loadData(fn);
        boolean changed = false;

        String[] commands = {"Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit"};

        String name, number, oldNumber;

        while (true) {
            int c = ui.getCommand(commands);
            switch (c) {
                case -1:
                    ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
                    break;
                case 0:
                    name = ui.getInfo("Enter name of person to be changed/added ");
                    if (name == null) {
                        break;
                    }
                    if (name.equals("")) {
                        break;
                    }
                    number = ui.getInfo("Enter number of person to be changed/added ");
                    oldNumber = pd.addOrChangeEntry(name, number);
                    changed = true;
                    if (oldNumber != null) {
                        ui.sendMessage(name + "'s old number was " + oldNumber);
                    }
                    System.out.println();
                    break;
                case 1:
                    name = ui.getInfo("Enter the name ");
                    number = pd.lookupEntry(name);
                    if (name != null && number == null) {
                        ui.sendMessage(name + " has no number");
                    } else if (name == null || name.equals("")) {
                        break;
                    } else {
                            ui.sendMessage(name + " has number " + number);
                        }

                    System.out.println();
                    break;
                case 2:
                    name = ui.getInfo("Enter the name ");
                    number = pd.lookupEntry(name);
                    if (number != null) {
                        String removedNumber = pd.removeEntry(name);
                        ui.sendMessage("Entry removed: number was " + removedNumber);
                    } else if (name == null) {
                        break;
                    } else {
                        ui.sendMessage("Entry does not exist or person has no number");
                    }
                    changed = true;
                    System.out.println();
                    break;
                case 3:
                    pd.save();
                    System.out.println();
                    changed = false;
                    break;
                case 4:
                    if (changed) {
                        String[] options = {"Yes", "No"};
                        ui.sendMessage("Are you sure you want to exit without saving?");
                        int i = ui.getCommand(options);
                        if (i == 0) {
                            return;
                        } else {
                            break;
                        }
                    }
                    return;
            }
            }
        }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fn = "csc220.txt";
        //PhoneDirectory pd = new ArrayBasedPD();
        PhoneDirectory pd = new SortedPD();
        UserInterface ui = new GUI("Phone Directory");
        //UserInterface ui = new ConsoleUI();
        processCommands(fn, ui, pd);
    }
}
