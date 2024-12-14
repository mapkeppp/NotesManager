package com.example.notesmanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesmanager.R
import com.example.notesmanager.data.model.Note
import com.example.notesmanager.ui.viewmodels.NoteViewModel

class NoteEditFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels()
    private val args: NoteEditFragmentArgs by navArgs()

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleEditText = view.findViewById(R.id.editTextTitle)
        contentEditText = view.findViewById(R.id.editTextContent)
        saveButton = view.findViewById(R.id.buttonSave)

        setupSaveButton()
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {
                if (args.noteId != -1L) {
                    viewModel.updateNote(Note(
                        id = args.noteId,
                        title = title,
                        content = content
                    ))
                } else {
                    viewModel.insertNote(title, content)
                }
                findNavController().navigateUp()
            }
        }
    }
}