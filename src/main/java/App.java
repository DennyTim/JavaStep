import view.ConsoleView;

public class App {
    public static void main(String[] args) {
//        Online table

        /*ConsoleView consoleView = new ConsoleView();
        consoleView.printOnlineTableData();*/

        //Flight search
        ConsoleView consoleView = new ConsoleView();
        consoleView.flightsService();

    }
}
