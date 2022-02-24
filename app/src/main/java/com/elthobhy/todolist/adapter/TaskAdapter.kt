package com.elthobhy.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elthobhy.todolist.R
import com.elthobhy.todolist.databinding.ItemTaskBinding
import com.elthobhy.todolist.model.Task

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemBinding = ItemTaskBinding.bind(view)
        fun bind(task: Task, listener: (Task) -> Unit) {
            itemBinding.tvTitleTask.text = task.mainTask?.title
            val subTaskAdapter = SubTaskAdapter()
            if(task.mainTask?.date !=null && task.mainTask!!.date!!.isNotEmpty()){
                showDateTask()
                itemBinding.tvDateTask.text = task.mainTask!!.date
            }else{
                hideDateTask()
            }

            if(task.subTask != null){
                showSubTasks()
                subTaskAdapter.setData(task.subTask!!)

                itemBinding.rvSubTask.adapter = subTaskAdapter

            }else{
                hideSubTasks()
            }
            itemBinding.btnDoneTask.setOnClickListener {
                if(task.mainTask?.isComplete!!){
                    inCompleteTask()
                    task.mainTask!!.isComplete = false
                }else{
                    completeTask()
                    task.mainTask!!.isComplete = true
                }
            }

            itemView.setOnClickListener {
                listener(task)
            }

            subTaskAdapter.onClick{
                listener(task)
            }
        }

        private fun completeTask() {
            itemBinding.btnDoneTask.setImageResource(R.drawable.ic_task_complete_black_24dp)
        }

        private fun inCompleteTask() {
            itemBinding.btnDoneTask.setImageResource(R.drawable.ic_done_task_black_24dp)
        }

        private fun hideSubTasks() {
            itemBinding.lineTask.visibility = View.GONE
            itemBinding.rvSubTask.visibility = View.GONE
        }

        private fun showSubTasks() {
            itemBinding.lineTask.visibility = View.VISIBLE
            itemBinding.rvSubTask.visibility = View.VISIBLE
        }

        private fun hideDateTask() {
            itemBinding.containerDateTask.visibility = View.GONE
        }

        private fun showDateTask() {
            itemBinding.containerDateTask.visibility = View.VISIBLE
        }

    }

    private lateinit var tasks: List<Task>
    private lateinit var listener: (Task) ->Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position], listener)
    }

    override fun getItemCount(): Int = tasks.size

    fun setData(tasks: List<Task>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun onCLick(listener: (Task) -> Unit){
        this.listener = listener
    }
}