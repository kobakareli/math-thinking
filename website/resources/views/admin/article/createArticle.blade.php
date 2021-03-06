@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Create Article</div>

                <div class="panel-body">
                    <form method="POST" enctype="multipart/form-data" action="/admin/articles/store">

                        {{ csrf_field() }}

                        <span class="language-marker">English:</span>
                        <div class="form-group">
                            <input name="title_en" class="form-control about-title" placeholder="English title" value="{{ old('title_en') }}"></input>
                            <textarea id="rich1" name="text_en" class="rich1 form-control about-text" placeholder="English description">{{ old('text_en') }}</textarea>
                        </div>

                        <span class="language-marker">Georgian:</span>
                        <div class="form-group">
                            <input name="title_ge" class="form-control about-title" placeholder="Georgian title" value="{{ old('title_ge') }}"></input>
                            <textarea id="rich2" name="text_ge" class="rich2 form-control about-text" placeholder="Georgian description">{{ old('text_ge') }}</textarea>
                        </div>

                        <span class="language-marker">Supercategories:</span>
                        <div class="form-group">
                            <select class="form-control" id="supercategories" name="supercategories[]" multiple>
                                @foreach($categories as $category)
                                    <option multiple="true" value="{{ $category->id }}">{{ $category->title_en }}</option>
                                @endforeach
                            </select>
                        </div>

                        <span class="language-marker">Subcategories:</span>
                        <div class="form-group categories-container">
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary pull-right">Save</button>
                        </div>

                    </form>

                    @include('admin/errors')
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
