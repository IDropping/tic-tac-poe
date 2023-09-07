package com.example.myapplicationbnhvg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private ActivityMainBinding binding;
    private LinearLayout board;
    private ArrayList<Button> squares = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.OnClickListener listener = (view)->{
            Button btn = (Button) view;
            if (!btn.getText().toString().equals("")) return;
            if (GameInfo.isTurn)
            {
                btn.setText(GameInfo.firstSymbol);
                int[] comb = calcWinnPosition(GameInfo.firstSymbol);
                if(comb != null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "winner is " + GameInfo.firstSymbol,
                            Toast.LENGTH_LONG).show();
                    hightLight(comb);
                }
                //squares.get(comb[i]).setB...
                //с циклом for
            }
            else {
                btn.setText(GameInfo.secondSymbol);
                int[] comb = calcWinnPosition(GameInfo.secondSymbol);
                if(comb != null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "winner is " + GameInfo.secondSymbol,
                            Toast.LENGTH_LONG).show();
                    hightLight(comb);
                }
            }
            GameInfo.isTurn = !GameInfo.isTurn;
        };
        setContentView(R.layout.activity_main);
        board = findViewById(R.id.board);
        generateBoard(3, 3, board);
        setListenerToSquares(listener);
        deleteall();
    }

    private void deleteall(){
        Button fck = findViewById(R.id.fck);
        fck.setOnClickListener((view)->{
            Toast.makeText(getApplicationContext(),"new game", Toast.LENGTH_LONG).show();
            for(Button square: squares){
                square.setText("");
                square.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.gray));
            }

        });
    }

    private void generateBoard(int rowCount, int columnCount, LinearLayout board) {
        //Генерация строчек от 0 до rowCount
        for (int row = 0; row < rowCount; row++) {
            //Создаем контейнер(нашу строку) и вносим ее в board
            LinearLayout rowContainer = generateRow(columnCount);
            board.addView(rowContainer);
        }
    }
    private void setListenerToSquares(View.OnClickListener listener){
        for(int i = 0; i < squares.size();i++)
            squares.get(i).setOnClickListener(listener);
    }

    //метод генерации строк для board
    public LinearLayout generateRow(int squaresCount) {
        LinearLayout rowContainer = new LinearLayout(getApplicationContext());
        rowContainer.setOrientation(LinearLayout.HORIZONTAL);
        rowContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        for (int square = 0; square < squaresCount; square++)
        {
            //Создаем кнопку для добавления в строку
            Button button = new Button(getApplicationContext());
            //Устанавливаем цвет с помощью tint
            button.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.gray));
            button.setWidth(convertToPixel(50));
            button.setHeight(convertToPixel(90));
            rowContainer.addView(button);
            squares.add(button);
        }
        return rowContainer;
    }

    public int convertToPixel (int digit)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(digit * density * 0.5);
    }

    public void hightLight(int [] comb){
        for(int i = 0; i<comb.length; i++){
            squares.get(comb[i]).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.green));
        }
    }

    public int [] calcWinnPosition(String symbol) {
        for (int i = 0; i < GameInfo.winCombination.length; i++) {
            boolean findComb = true;
            for (int j = 0; j < GameInfo.winCombination[0].length; j++) {
                int index = GameInfo.winCombination[i][j];
                if (!squares.get(index).getText().toString().equals(symbol)) {
                    findComb = false;
                    //squares.get(comb[i]).setBBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.green));
                    //с циклом for
                    break;
                }
            }
            if (findComb) return GameInfo.winCombination[i];
        }
        return null;
    }
}