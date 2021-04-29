package com.example.letterlab.ui.deletealllearned

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllLearnedDialogFragment : DialogFragment() {
    private val viewModel: DeleteLearnedViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Do you really want to delete all of the learned words?")
            .setNegativeButton("cancel", null)
            .setPositiveButton("yes") { _, _ ->
                viewModel.onConfirmClick()
            }
            .create()

}