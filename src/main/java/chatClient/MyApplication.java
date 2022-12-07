package chatClient;

import chatClient.service.ChatClientService;
import chatClient.service.IChatClientService;
import chatClient.utility.Logger;
import chatClient.view.ViewHandler;
import chatClient.view.viewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            IChatClientService iChatClientService = new ChatClientService();
            ViewModelFactory viewModelFactory = new ViewModelFactory(iChatClientService);
            ViewHandler viewHandler = new ViewHandler(viewModelFactory);
            viewHandler.start(primaryStage);
        } catch (Exception e) {
            Logger.getInstance().log("Client didnt start as expected");
            e.printStackTrace();
        }
    }
}
