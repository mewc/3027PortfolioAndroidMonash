package croft.todo.Calculator;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import croft.todo.R;

//controller
public class CalculatorMain extends AppCompatActivity {

    String selectedOperation;
    private EditText firstNumberInput;
    private EditText secondNumberInput;
    private TextView firstNumView;
    private TextView secondNumView;
    private TextView opView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_main);
        setTitle("Simple Calculator");


        Button calcBtn = (Button) findViewById(R.id.calculate);
        calcBtn.setEnabled(false);
        calcBtn.setBackgroundColor(Color.parseColor("#ff5a595b"));


        firstNumberInput = (EditText) findViewById(R.id.firstNumberInput);
        secondNumberInput = (EditText) findViewById(R.id.secondNumberInput);
        firstNumView = (TextView) findViewById(R.id.firstNumView);
        secondNumView = (TextView) findViewById(R.id.secondNumView);

        firstNumberInput.addTextChangedListener(w);
        secondNumberInput.addTextChangedListener(w);

        opView = (TextView) (findViewById(R.id.selOperView));
        final Button bAddition = (Button) findViewById(R.id.additionButton);
        final Button bSubtract = (Button) findViewById(R.id.subtractButton);
        final Button bMultiply = (Button) findViewById(R.id.multiplyButton);
        final Button bDivide = (Button) findViewById(R.id.divideButton);



        View.OnClickListener opButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSelectedOperation( ((Button) v).getText().toString());
                System.out.println(getSelectedOperation() + "onCLICKLISTENER WORKING");
                opView.setText(getSelectedOperation());
                if(calcEnableAllowed()){
                    enableCalculateButton();
                }
            }
        };

        bAddition.setOnClickListener(opButtonListener);
        bSubtract.setOnClickListener(opButtonListener);
        bMultiply.setOnClickListener(opButtonListener);
        bDivide.setOnClickListener(opButtonListener);

    }

    private final TextWatcher w = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //TODO: set limits for the number of characters for input and output.
            if(calcEnableAllowed()){
                enableCalculateButton();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //allows calculation if requirements are met
            firstNumView.setText(firstNumberInput.getText().toString());
            secondNumView.setText(secondNumberInput.getText().toString());

        }
    };

    public void calculateButtonPressed(View v) {

        //get text from fields
        double firstNumber = Double.valueOf(firstNumberInput.getText().toString());
        double secondNumber = Double.valueOf(secondNumberInput.getText().toString());

        Calculator calc = new Calculator(firstNumber, secondNumber, selectedOperation);

        TextView resultText = (TextView) findViewById(R.id.resultDisplayer);
        resultText.setText(String.valueOf(calc.performOperation()));

        Double result = calc.roundToTwoDecimalPlaces(calc.performOperation());

        resultText.setText(String.valueOf(result));

        //TODO: store history of calculations in a file, so user can retrieve them
    }



    public boolean calcEnableAllowed(){
        return selectedOperation != null &&
                secondNumberInput.getText().toString().length() > 0 &&
                firstNumberInput.getText().toString().length() > 0;
    }

    public void enableCalculateButton() {
        Button c = (Button) findViewById(R.id.calculate);
        c.setBackgroundColor(getPrimary());
        c.setEnabled(true);
    }

    public int getAccent() {
        return ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
    }

    public int getPrimary() {
        return ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
    }

    public int getPrimDark() {
        return ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
    }

    public int getUnfocussed(){
        return ContextCompat.getColor(getApplicationContext(), Color.parseColor("#5A595B"));
    }

    public void setSelectedOperation(String operation) {
        selectedOperation = operation;
    }

    public String getSelectedOperation(){
        return selectedOperation;
    }
}




