# Greenhouse Manager

## Description

The main premise of **Greenhouse Manager** is to buy and care for plants and sell them to make a profit. To win the 
game the user must use revenue from plant sales to pay off their business loan debt. Once the debt is paid, *the user 
wins the game*. The audience for this game is anyone interested in simulation games or plants. I chose this project because in 
my many personal projects I have never developed a game before and would like to challenge myself by trying to develop 
something new for myself. I specifically chose simulating plant care because I enjoy the agriculture feature in main 
stream games like Minecraft and Animal Crossing.

## User Stories

### Phase 1 (Implemented)
1. ~~As a user, I want to be able to purchase seeds~~
2. As a user, I want to be able to ~~plant seeds~~ buy plants in my greenhouse
3. As a user, I want to watch the baby plant grow into a mature plant over time
4. As a user, I want the option to water my plant as it grows
5. As a user, I want the option to sell my plant for money
6. As a user, I want to see a list of the plants I have in my green house along with their age and hydration level

## Phase 2 (Implemented)
1. As a user, I want the option to start a new game
2. As a user, I want the option to be able to save the state of my game 
3. As a user, I want the option to restore a previously saved game when I run the game

### Phase 3 (Implemented)
1. As a user, I want the option to pay off my loan using the money I generate from sales
2. As a user, if my debt reaches zero, I want to *win the game*
3. As a user, I want to be limited to the number of plants I have at a given moment by the number of pots I own
4. As a user, I want to be able to buy more pots to allow me to grow more plants in the game
5. **Seeds have been deprecated, user just buys plants that start at age zero**
6. 1. As a user, I want the plant to be removed from the greenhouse if hydration reaches zero
7. As a user, I want the option to buy a cactus.
> The cactus plant grows slowly and requires less frequent watering.
The cactus can be sold for a profit when it reaches a mature age, otherwise it is sold for a loss.
8. As a user, I want to be able to plant a flower
> The flower plant grows faster compared to the cactus but requires more frequent watering.
    The flower can be sold for a profit when it reaches a mature age. otherwise it is sold for a loss.
    When the flower is ready to be sold it blooms to a random color, that can influence the sale price.
    The more rare the color it blooms to, the higher the sale price of the flower.
9. As a user, I want to be able to play the game with a GUI

# Instructions for Grader
- when you run the app, press "new game" and enter a name for the game file
- you can generate the action of "adding X to Y" by pressing "Buy Cactus" or "Buy Flower"
- You should see visual component which will be a picture of a pot with a plant growing as the plant
  (either cactus or flower) ages with time.
- You can right-click on the image of the plant and pressing "water plant", this will reset the hydration level
- You can generate the action of "removing X from Y" by right-clicking on a plant and selecting "sell plant", I
  recommend waiting a while for the plant to mature so it can be sold for a profit
- You can press "Save Game" to save the state of the game
- After saving, you can quit the game and re-open the game. 
- On the splash screen select "load game" to load a game file Enter the name you used when creating a new game, 
here the game will load from the point it was saved last.


