package com.mishka.note;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private EditText editTitle, editContent;
    private Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        // Отримання даних нотатки
        Intent intent = getIntent();
        String noteTitle = intent.getStringExtra("note_title");
        String noteContent = intent.getStringExtra("note_content");
        int notePosition = intent.getIntExtra("note_position", -1);

        if (noteTitle != null) {
            editTitle.setText(noteTitle);
        }
        if (noteContent != null) {
            editContent.setText(noteContent);
        }

        // Обробка кнопки "Зберегти"
        btnSave.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("note_title", editTitle.getText().toString());
            resultIntent.putExtra("note_content", editContent.getText().toString());
            resultIntent.putExtra("note_position", notePosition);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Обробка кнопки "Видалити"
        btnDelete.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("note_position", notePosition);
            setResult(RESULT_FIRST_USER, resultIntent); // Код для видалення
            finish();
        });
    }
}
