# TANK

### Jogo multiplayer em Java.

Para jogar siga os seguintes passos:

1. Execute esta classe para subir o **Servidor RMI**:

        br.com.staroski.games.tank.server.StartupTankServer
        
   Observe o valor do campo **"Instância do jogo"**.

2. Execute esta classe para subir o **Cliente AWT/Swing**:

       br.com.staroski.games.tank.client.StartupTankClient

   No campo **"Instância do jogo"**, insira o valor do mesmo campo apresentado no **Servidor RMI**.


### Atenção:

O jogo utiliza a **porta 8008**, se você tiver problemas para acessar esta porta, altere o valor da contante **PORT** na seguinte classe:
    
    br.com.staroski.games.ObjectBinder

### Teclas

Virar para a esquerda ou direita:

    A, D, Seta pra Esquerda, Seta pra Direita

Acelerar:

    W, Seta pra Cima

Descelerar:

    S, Seta pra Baixo

Mirar:

    Cursor do Mouse

Atirar:

    Ctrl, J, Clique do Mouse


### Bom divertimento!

![Screenshot](screenshot.png)
