<select class="form-control" id="tasks" name="tasks[]" multiple>
    @foreach($category->tasks as $task)
        <option multiple="true" value="{{ $task->id }}">{{ $task->title_en }}</option>
    @endforeach
</select>
