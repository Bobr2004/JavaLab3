package com.mishka.note;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private ArrayList<Note> notes;

    private static final int REQUEST_CODE_EDIT_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notes = new ArrayList<>();
        adapter = new NotesAdapter(notes, this::openEditNoteActivity);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAddNote = findViewById(R.id.fabAddNote);
        fabAddNote.setOnClickListener(view -> openEditNoteActivity(null, -1));
    }

    // Відкриття EditNoteActivity
    private void openEditNoteActivity(Note note, int position) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        if (note != null) {
            intent.putExtra("note_title", note.getTitle());
            intent.putExtra("note_content", note.getContent());
        }
        intent.putExtra("note_position", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_NOTE) {
            if (resultCode == RESULT_OK) {
                // Зберегти або оновити нотатку
                String title = data.getStringExtra("note_title");
                String content = data.getStringExtra("note_content");
                int position = data.getIntExtra("note_position", -1);

                if (position == -1) {
                    // Додати нову нотатку
                    notes.add(new Note(notes.size() + 1, title, content, "2024-11-20"));
                } else {
                    // Оновити існуючу нотатку
                    Note note = notes.get(position);
                    note.setTitle(title);
                    note.setContent(content);
                }
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_FIRST_USER) {
                // Видалити нотатку
                int position = data.getIntExtra("note_position", -1);
                if (position != -1) {
                    notes.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}

