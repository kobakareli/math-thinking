<select class="form-control" id="categories" name="categories">
    @foreach($superCategory->categories as $category)
        <option value="{{ $category->id }}">{{ $category->{'title_' . App::getLocale()} }}</option>
    @endforeach
</select>
