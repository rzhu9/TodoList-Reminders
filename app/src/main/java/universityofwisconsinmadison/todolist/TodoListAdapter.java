package universityofwisconsinmadison.todolist;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import universityofwisconsinmadison.todolist.Utils.UIUtils;
import universityofwisconsinmadison.todolist.model.Todo;

/**
 * Created by Ruihao on 6/20/17.
 */

public class TodoListAdapter extends BaseAdapter {

    private MainActivity mainActivity;
    private List<Todo> data;

    public TodoListAdapter(MainActivity mainActivity, List<Todo> data) {
        this.mainActivity = mainActivity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mainActivity.getLayoutInflater().inflate(R.layout.main_list_item, viewGroup, false);

            vh = new ViewHolder();
            vh.todoText = (TextView) convertView.findViewById(R.id.main_list_item_text);
            vh.doneCheckbox = (CheckBox) convertView.findViewById(R.id.main_list_item_check);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }
        final Todo todo = (Todo) getItem(i);
        vh.todoText.setText(todo.text);
        UIUtils.setTextViewStrikeThrough(vh.todoText, todo.done);

        vh.doneCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mainActivity.updateTodo(i, b);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, TodoEditActivity.class);
                intent.putExtra(TodoEditActivity.KEY_TODO, todo);
                mainActivity.startActivityForResult(intent, MainActivity.REQ_CODE_TODO_EDIT);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView todoText;
        CheckBox doneCheckbox;
    }
}
