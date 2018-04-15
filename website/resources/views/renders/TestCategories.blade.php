<select class="form-control" id="categories" name="categories[]" multiple>
    @foreach($superCategory->categories as $category)
        <option multiple="true" value="{{ $category->id }}">{{ $category->title_en }}</option>
    @endforeach
</select>
