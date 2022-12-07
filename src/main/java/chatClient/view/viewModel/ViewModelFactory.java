package chatClient.view.viewModel;

import chatClient.service.IChatClientService;

public class ViewModelFactory {
    private final ChatScreenViewModel chatScreenViewModel;
    private final ForgotPasswordViewModel forgotPasswordViewModel;
    private final LoginScreenViewModel loginScreenViewModel;
    private final RegisterScreenViewModel registerScreenViewModel;

    public ViewModelFactory(IChatClientService iChatClientService) {
        this.chatScreenViewModel = new ChatScreenViewModel(iChatClientService);
        this.forgotPasswordViewModel = new ForgotPasswordViewModel(iChatClientService);
        this.loginScreenViewModel = new LoginScreenViewModel(iChatClientService);
        this.registerScreenViewModel = new RegisterScreenViewModel(iChatClientService);
    }

    public ChatScreenViewModel getChatScreenViewModel() {
        return chatScreenViewModel;
    }

    public ForgotPasswordViewModel getForgotPasswordViewModel() {
        return forgotPasswordViewModel;
    }

    public LoginScreenViewModel getLoginScreenViewModel() {
        return loginScreenViewModel;
    }

    public RegisterScreenViewModel getRegisterScreenViewModel() {
        return registerScreenViewModel;
    }
}
