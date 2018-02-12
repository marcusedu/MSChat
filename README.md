# MSChat
MSChat á uma biblioteca para dar suporte a criação de chat facilmente
Simplificamos o processo de criação de chat, é uma otima lib para validar alguma ideia ou mesmo para uso definitivo.

![alt text](https://github.com/marcusedu/MSChat/blob/master/chatexample/chat_example.gif "Exemplo")

### Alguns recursos:
- Feed de chat
- Balão que marca o dia da conversa
- Copiar texto do chat
- Rolar até o inicio da conversa
- Usuario está digitando
- Indicação do dia da mensagem no top do feed

### Recursos futuros:
- Previa de sites
- Envio de imagem
- Envio de audio
- Envio de arquivos
- Personalização do balão de mensagem
- Suporte a Bots
- Suporte a formulario
- Opções

## Como usar

Importar

```gradle
android {
compileOptions {
        targetCompatibility 1.8
        sourceCompatibility 1.8
    }
  }
dependencies {
    //...
    implementation 'info.marcussoftware:mschat:0.1-alpha'
}
```

Coloque o MSChatView no seu layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="info.marcussoftware.mschatexample.MainActivity">

    <info.marcussoftware.mschat.view.MSChatView
        android:id="@+id/mainMSChat"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```

Busque sua referencia no codigo.

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msChatView = findViewById(R.id.mainMSChat);
        //Você precisa implementar e passar um presenter, do contrario a lib irá lançar uma exceção
        msChatView.setPresenter(this);
        //Você precisa informar qual o id do usuario que está enviando a mensagem
        //para que a mensagem seja exibida como dele
        msChatView.setSenderUserID(myUserId);
    }
    
    @Override
    public void userTyping(boolean typing, @Nullable String text) {
        //Esse metodo será chamado toda vez que o usuario estiver digitando algo.
        //Você pode usar para informar ao destinatario que o rementente está digitando.
    }

    @Override
    public void userSendMessage(Calendar instance, String s) {
        //Assim que o usuario clica em enviar uma mensagem,
        //você a recebe aqui, ela não vai automaticamente para o feed.
        //Para que você possa processar a mensagem antes de enviar.
        
        //Você precisa implementar a interface Message.
        MinhaMensagem message = new MinhaMensagem();
        //O metodo showMessage você usa para exibir a mensagem no feed
        msChatView.showMessage(message.setDateTime(instance).setMessage(s).setUserName("Marcus").setUserId(myUserId));
        msChatView.showMessage(message.setDateTime(instance).setMessage(s).setUserName("Cliente").setUserId("13"));
    }
```
