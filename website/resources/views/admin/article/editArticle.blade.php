@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Edit Article</div>

                <div class="panel-body">
                    <form method="POST" enctype="multipart/form-data" action="/admin/articles/update/{{ $article->id }}">

                        {{ csrf_field() }}
                        {{ method_field('PATCH') }}

                        <span class="language-marker">English:</span>
                        <div class="form-group">
                            <input name="title_en" class="form-control about-title" placeholder="English title" value="{{ $article->title_en }}"></input>
                            <textarea id="rich1" name="text_en" class="rich1 form-control about-text" placeholder="English description">{{ $article->text_en }}</textarea>
                        </div>

                        <span class="language-marker">Georgian:</span>
                        <div class="form-group">
                            <input name="title_ge" class="form-control about-title" placeholder="Georgian title" value="{{ $article->title_ge }}"></input>
                            <textarea id="rich2" name="text_ge" class="rich2 form-control about-text" placeholder="Georgian description">{{ $article->text_ge }}</textarea>
                        </div>

                        <span class="language-marker">Categories:</span>
                        @foreach($article->categories as $category)
                            <div class="about-container" style="margin-bottom: 20px">
                                <span class="about-title">{{ $article->title_en }}</span>
                            </div>
                        @endforeach
                        <div class="form-group">
                            <select class="form-control" id="categories" name="categories[]" multiple>
                                @foreach($categories as $category)
                                    <option multiple="true" value="{{ $category->id }}">{{ $category->title_en }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>

                    </form>

                    @include('admin/errors')

                </div>
            </div>
        </div>
    </div>
</div>

@endsection

@push('scripts')
    <script>
        var articleCategories = @json($article->categories);
    </script>
@endpush
