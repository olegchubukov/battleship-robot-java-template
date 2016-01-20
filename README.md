# battleship-robot-java-template
Battleship server is online on http://battle.codes/

This is a very stupid battleship robot. It will always place ships the same place and shoot random at the enemy. 

# Battleship game
## Rules
The boards are 12 x 12 squares

In planning mode the player needs to place 6 ships.
* 1 ship with length of 2 squares.
* 2 ships with length of 3 squares.
* 2 ships with length of 4 squares.
* 1 ship with length of 5 squares.

In playing mode, each player shoots at the enemy (the other player) board. When shooting at the enemy, 
one of three things might happen.

1. Splash - You missed an enemy ship.
2. Pang! - You hit an enemy ship.
3. Sunk! - You hit and sink an enemy ship

The player that sinks all the enemy ships first, wins the game.

## How to test your robot
1. Log into [battle.codes](http://battle.codes)
2. When logged in, you get a Json Web Token (jwt)
3. Run the battleship robot with the jwt as Program Argument.
4. Go back to the page with you jwt and refresh (F5) it. Now a test button should appear.
5. Click button.

## WebSockets API.

Set ship
```
{
    "class": "game.messages.SetShipMessage",
    "ship": {
        "coordinates": [
            {"x": 1, "y": 2},
            {"x": 2, "y": 2}
        ]
    }
}
```

When the game is started, the client gets the following message from the server:
```
{
    "class":"game.broadcast.GameIsStarted",
}
```

When it is you turn, your robot will get the following message
{
    "class":"game.messages.ItsYourTurnMessage"
}

When it is your turn, you can shoot at the enemy
```
{
    "class": "game.messages.ShootMessage",
    "coordinate": {
        "x": 4,
        "y": 8
    }
}
```

When you have shot at the enemy board, your robot gets one of the following result messages:
```
{
    "class":"game.result.ShootResult",
    "ok":true,
    "hit":"SPLASH"
}
```

or
```
{
    "class":"game.result.ShootResult",
    "ok":true,
    "hit":"BANG"
}
```
or

```
{
    "class":"game.result.ShootResult",
    "ok":true,
    "hit":"SUNK"
}
```

When the game is over, the players get the following message
```
{
    "class":"game.broadcast.GameOver",
    "andTheWinnerIs":"Lars Aaberg"
}
```
Search and destroy!
