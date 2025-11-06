package ru.samsung.calculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNumberInput;
    private TextInputLayout textInputLayout;
    private TextView tvNumbersList;
    private TextView tvMedian;
    private TextView tvAverage;
    private TextView tvMax;
    private TextView tvMin;
    private TextView tvRange;

    private MaterialButton btnAdd;
    private MaterialButton btnRemoveLast;
    private MaterialButton btnSort;

    private List<Double> numbersList = new ArrayList<>();
    private boolean isSorted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupClickListeners();
        updateStatistics();
    }

    private void initViews() {
        etNumberInput = findViewById(R.id.etNumberInput);
        textInputLayout = findViewById(R.id.textInputLayout);
        tvNumbersList = findViewById(R.id.tvNumbersList);
        tvMedian = findViewById(R.id.tvMedian);
        tvAverage = findViewById(R.id.tvAverage);
        tvMax = findViewById(R.id.tvMax);
        tvMin = findViewById(R.id.tvMin);
        tvRange = findViewById(R.id.tvRange);

        btnAdd = findViewById(R.id.btnAdd);
        btnRemoveLast = findViewById(R.id.btnRemoveLast);
        btnSort = findViewById(R.id.btnSort);
    }

    private void setupClickListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber();
            }
        });

        btnRemoveLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastNumber();
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortNumbers();
            }
        });
    }

    private void addNumber() {
        String input = etNumberInput.getText().toString().trim();

        if (TextUtils.isEmpty(input)) {
            textInputLayout.setError("Введите число");
            return;
        }

        try {
            double number = Double.parseDouble(input);
            numbersList.add(number);
            isSorted = false;

            etNumberInput.setText("");
            textInputLayout.setError(null);

            updateNumbersList();
            updateStatistics();

        } catch (NumberFormatException e) {
            textInputLayout.setError("Некорректное число");
        }
    }

    private void removeLastNumber() {
        if (!numbersList.isEmpty()) {
            numbersList.remove(numbersList.size() - 1);
            updateNumbersList();
            updateStatistics();
        }
    }

    private void sortNumbers() {
        if (!numbersList.isEmpty()) {
            Collections.sort(numbersList);
            isSorted = true;
            updateNumbersList();
            updateStatistics();
        }
    }

    private void updateNumbersList() {
        if (numbersList.isEmpty()) {
            tvNumbersList.setText("Список чисел пуст");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbersList.size(); i++) {
            sb.append(formatNumber(numbersList.get(i)));
            if (i < numbersList.size() - 1) {
                sb.append(", ");
            }
            // Перенос строки после каждых 3 чисел для лучшей читаемости
            if ((i + 1) % 3 == 0) {
                sb.append("\n");
            }
        }

        tvNumbersList.setText(sb.toString());
    }

    private void updateStatistics() {
        if (numbersList.isEmpty()) {
            resetStatistics();
            return;
        }

        // Среднее значение
        double average = calculateAverage();
        tvAverage.setText(formatNumber(average));

        // Медиана
        double median = calculateMedian();
        tvMedian.setText(formatNumber(median));

        // MAX и MIN
        double max = calculateMax();
        double min = calculateMin();
        tvMax.setText(formatNumber(max));
        tvMin.setText(formatNumber(min));

        // Размах
        double range = max - min;
        tvRange.setText(formatNumber(range));
    }

    private void resetStatistics() {
        tvAverage.setText("0");
        tvMedian.setText("0");
        tvMax.setText("0");
        tvMin.setText("0");
        tvRange.setText("0");
        tvNumbersList.setText("Тут появятся ниже введенные числа");
    }

    private double calculateAverage() {
        double sum = 0;
        for (double num : numbersList) {
            sum += num;
        }
        return sum / numbersList.size();
    }

    private double calculateMedian() {
        if (numbersList.isEmpty()) return 0;

        List<Double> tempList = new ArrayList<>(numbersList);
        Collections.sort(tempList);

        int size = tempList.size();
        if (size % 2 == 0) {
            // Для четного количества чисел - среднее двух центральных
            return (tempList.get(size / 2 - 1) + tempList.get(size / 2)) / 2.0;
        } else {
            // Для нечетного количества - центральное число
            return tempList.get(size / 2);
        }
    }

    private double calculateMax() {
        double max = numbersList.get(0);
        for (double num : numbersList) {
            if (num > max) max = num;
        }
        return max;
    }

    private double calculateMin() {
        double min = numbersList.get(0);
        for (double num : numbersList) {
            if (num < min) min = num;
        }
        return min;
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%.2f", number);
        }
    }
}