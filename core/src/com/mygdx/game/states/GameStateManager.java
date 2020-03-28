package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    //стэк это  структура данных, котрая позволяет следить за состоянием текущего окна приложеничя
    // реалезует подход ''последний вошел-первый вышел''
    private Stack<State> states;



    //создаем конструктор
    public GameStateManager() {
        states = new Stack<State>();
    }



    //void озночает что метод не возваращает  значение, не смысла писать return
    public void push(State state){
        states.push(state);
    }


    //метод pop удалять последнее окно в стэке
    public void pop() {
        states.pop().dispose();

    }


    //удаляет последнее окно с конца и добовляет новое
    public void set(State state){
        states.pop().dispose();
        states.push(state);

    }

    public  void update(float dt){
        //peek() - метод который просматнривает верхний алимен стэка ,но не удаляет его
        states.peek().update(dt);
    }

    //перерисовка текстур через поступаемый извие SpriteBatch
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
