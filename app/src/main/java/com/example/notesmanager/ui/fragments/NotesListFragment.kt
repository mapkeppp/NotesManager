package com.example.notesmanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmanager.R
import com.example.notesmanager.ui.adapters.NoteAdapter
import com.example.notesmanager.ui.viewmodels.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        fab = view.findViewById(R.id.fabAddNote)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeNotes()
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter { note ->
            val action = NotesListFragmentDirections
                .actionNotesListFragmentToNoteEditFragment()
                .setNoteId(note.id)
            findNavController().navigate(action)
        }

        recyclerView.apply {
            this.adapter = this@NotesListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupFab() {
        fab.setOnClickListener {
            val action = NotesListFragmentDirections
                .actionNotesListFragmentToNoteEditFragment()
                .setNoteId(-1L)
            findNavController().navigate(action)
        }
    }

    private fun observeNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allNotes.collect { notes ->
                adapter.submitList(notes)
            }
        }
    }
}