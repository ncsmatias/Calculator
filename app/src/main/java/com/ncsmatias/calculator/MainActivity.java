package com.ncsmatias.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ncsmatias.calculator.viewModels.MainViewModel;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

enum numberValues {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    ;

    private String number;

    numberValues(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }
}

enum expressionValues {
    DIVISION("/"),
    MULTPLY("*"),
    SUB("-"),
    PLUS("+"),
    DOT("."),
    OPENPARENTHESES("("),
    CLOSEPARENTHESES(")"),
    ;

    private String expression;

    expressionValues(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }
}

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNumberZero;
    private Button btnNumberOne;
    private Button btnNumberTwo;
    private Button btnNumberThree;
    private Button btnNumberFour;
    private Button btnNumberFive;
    private Button btnNumberSix;
    private Button btnNumberSeven;
    private Button btnNumberEight;
    private Button btnNumberNine;

    private Button btnClear;
    private Button btnOpenParentheses;
    private Button btnCloseParentheses;
    private Button btnDot;
    private Button btnDivision;
    private Button btnMultply;
    private Button btnSub;
    private Button btnPlus;
    private Button btnEqual;

    private TextView txtExpression;
    private TextView txtResult;

    private ImageView imgBackspace;

    private MainViewModel model;
    private String expressionLabel = "";
    private String resultLabel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        model = new ViewModelProvider(this).get(MainViewModel.class);

        final Observer<String> expressionObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newExpression) {
                expressionLabel = newExpression;

                txtExpression.setText(expressionLabel);
            }
        };

        final Observer<String> resultObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newResult) {
                resultLabel = newResult;
                txtResult.setText(resultLabel);
            }
        };

        model.getCurrentExpression().observe(this, expressionObserver);
        model.getCurrentResult().observe(this, resultObserver);


        btnNumberZero.setOnClickListener(this);
        btnNumberOne.setOnClickListener(this);
        btnNumberTwo.setOnClickListener(this);
        btnNumberThree.setOnClickListener(this);
        btnNumberFour.setOnClickListener(this);
        btnNumberFive.setOnClickListener(this);
        btnNumberSix.setOnClickListener(this);
        btnNumberSeven.setOnClickListener(this);
        btnNumberEight.setOnClickListener(this);
        btnNumberNine.setOnClickListener(this);

        btnOpenParentheses.setOnClickListener(this);
        btnCloseParentheses.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnDivision.setOnClickListener(this);
        btnMultply.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnPlus.setOnClickListener(this);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getCurrentExpression().setValue("");
                model.getCurrentResult().setValue("");
            }
        });

        imgBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearExpression();
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Expression expression = new ExpressionBuilder(expressionLabel).build();

                    double result = expression.evaluate();

                    model.getCurrentResult().setValue(String.valueOf(result));
                } catch (Exception e) {
                    System.out.println(e);

                    showSnackbar("Formato da expressão inválido", view);
                }
            }
        });
    }

    public void clearExpression() {

        if(!expressionLabel.isEmpty()) {
            String auxExpression = expressionLabel.substring(0, expressionLabel.length() - 1);
            model.getCurrentExpression().setValue(auxExpression);
            model.getCurrentResult().setValue("");
        }
    }

    public boolean isOperation(char operator) {
        switch (operator) {
            case '/':
            case '*':
            case '+':
            case '-':
            case '.':
                return true;
            default: return false;
        } 
    }

    public void viewExpression(String expression, View view) {

        int lengthExpression = expressionLabel.length();

        if(lengthExpression == 0 && isOperation(expression.charAt(0))) {
            showSnackbar("Formato usado inválido", view);
            return;
        }

        if(lengthExpression >= 1 && isOperation(expression.charAt(0)) && isOperation(expressionLabel.charAt(lengthExpression - 1))) {
            clearExpression();
        }

        model.getCurrentExpression().setValue(expressionLabel + expression);
        model.getCurrentResult().setValue(" ");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.numberZeroButton:
                viewExpression(numberValues.ZERO.getNumber(), view);
                break;
            case R.id.numberOneButton:
                viewExpression(numberValues.ONE.getNumber(), view);
                break;
            case R.id.numberTwoButton:
                viewExpression(numberValues.TWO.getNumber(), view);
                break;
            case R.id.numberThreeButton:
                viewExpression(numberValues.THREE.getNumber(), view);
                break;
            case R.id.numberFourButton:
                viewExpression(numberValues.FOUR.getNumber(), view);
                break;
            case R.id.numberFiveButton:
                viewExpression(numberValues.FIVE.getNumber(), view);
                break;
            case R.id.numberSixButton:
                viewExpression(numberValues.SIX.getNumber(), view);
                break;
            case R.id.numberSevenButton:
                viewExpression(numberValues.SEVEN.getNumber(), view);
                break;
            case R.id.numberEightButton:
                viewExpression(numberValues.EIGHT.getNumber(), view);
                break;
            case R.id.numberNineButton:
                viewExpression(numberValues.NINE.getNumber(), view);
                break;
            case R.id.dotButton:
                viewExpression(expressionValues.DOT.getExpression(), view);
                break;
            case R.id.openParenthesesButton:
                viewExpression(expressionValues.OPENPARENTHESES.getExpression(), view);
                break;
            case R.id.closeParenthesesButton:
                viewExpression(expressionValues.CLOSEPARENTHESES.getExpression(), view);
                break;
            case R.id.divisionButton:
                viewExpression(expressionValues.DIVISION.getExpression(), view);
                break;
            case R.id.multiplyButton:
                viewExpression(expressionValues.MULTPLY.getExpression(), view);
                break;
            case R.id.subButton:
                viewExpression(expressionValues.SUB.getExpression(), view);
                break;
            case R.id.plusButton:
                viewExpression(expressionValues.PLUS.getExpression(), view);
                break;
            default: break;
        }
    }

    private void initComponents () {
        btnNumberZero = findViewById(R.id.numberZeroButton);
        btnNumberOne = findViewById(R.id.numberOneButton);
        btnNumberTwo = findViewById(R.id.numberTwoButton);
        btnNumberThree = findViewById(R.id.numberThreeButton);
        btnNumberFour = findViewById(R.id.numberFourButton);
        btnNumberFive = findViewById(R.id.numberFiveButton);
        btnNumberSix = findViewById(R.id.numberSixButton);
        btnNumberSeven = findViewById(R.id.numberSevenButton);
        btnNumberEight = findViewById(R.id.numberEightButton);
        btnNumberNine = findViewById(R.id.numberNineButton);

        btnClear = findViewById(R.id.clearButton);
        btnOpenParentheses = findViewById(R.id.openParenthesesButton);
        btnCloseParentheses = findViewById(R.id.closeParenthesesButton);
        btnDot = findViewById(R.id.dotButton);
        btnDivision = findViewById(R.id.divisionButton);
        btnMultply = findViewById(R.id.multiplyButton);
        btnSub = findViewById(R.id.subButton);
        btnPlus = findViewById(R.id.plusButton);
        btnEqual = findViewById(R.id.equalButton);

        txtExpression = findViewById(R.id.expressionTextView);
        txtResult = findViewById(R.id.resultTextView);

        imgBackspace = findViewById(R.id.backSpaceImageView);

    }

    public void showSnackbar(String text, View view) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

}











