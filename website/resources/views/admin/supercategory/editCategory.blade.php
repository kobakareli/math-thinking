@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Edit Super Category</div>

                <div class="panel-body">
                    <form method="POST" action="/admin/supercategories/update/{{ $category->id }}">
                        {{ method_field('PATCH') }}
                        {{ csrf_field() }}

                        <span class="language-marker">English:</span>
                        <div class="form-group">
                            <input name="title_en" class="form-control title" placeholder="English title" value="{{ $category->title_en }}"></input>
                        </div>

                        <span class="language-marker">Georgian:</span>
                        <div class="form-group">
                            <input name="title_ge" class="form-control title" placeholder="Georgian title" value="{{ $category->title_ge }}"></input>
                        </div>

                        <span class="language-marker">Categories:</span>
                        @foreach($category->categories as $subcategory)
                            <div class="about-container" style="margin-bottom: 20px">
                                <span class="about-title">{{ $subcategory->title_en }}</span>
                            </div>
                        @endforeach
                        <div class="form-group">
                            <select class="form-control" id="categories" name="categories[]" multiple>
                                @foreach($categories as $subcategory)
                                    <option multiple="true" value="{{ $subcategory->id }}">{{ $subcategory->title_en }}</option>
                                @endforeach
                            </select>
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

@push('scripts')
    <script>
        var subCategories = @json($category->categories);
    </script>
@endpush
