@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Edit Test</div>

                <div class="panel-body">
                    <form method="POST" enctype="multipart/form-data" action="/admin/tests/update/{{ $test->id }}">

                        {{ csrf_field() }}
                        {{ method_field('PATCH') }}

                        <span class="language-marker">English:</span>
                        <div class="form-group">
                            <input name="title_en" class="form-control about-title" placeholder="English title" value="{{ $test->title_en }}"></input>
                        </div>

                        <span class="language-marker">Georgian:</span>
                        <div class="form-group">
                            <input name="title_ge" class="form-control about-title" placeholder="Georgian title" value="{{ $test->title_ge }}"></input>
                        </div>


                        <span class="language-marker">Select Super Category:</span>
                        <div class="form-group">
                            <select class="form-control" id="supercategories" name="supercategories[]" multiple>
                                @foreach($categories as $category)
                                    <option multiple="true" value="{{ $category->id }}">{{ $category->title_en }}</option>
                                @endforeach
                            </select>
                        </div>

                        <span class="language-marker">Select Category:</span>
                        <div class="form-group categories-container">
                            <select class="form-control" id="categories" name="categories[]" multiple>
                                @foreach($subcategories as $category)
                                    <option multiple="true" value="{{ $category->id }}">{{ $category->title_en }}</option>
                                @endforeach
                            </select>
                        </div>

                        <span class="language-marker">Select Tasks:</span>
                        <div class="form-group tasks-container">
                            <select class="form-control" id="tasks" name="tasks[]" multiple>
                                @foreach($test->categories[0]->tasks as $task)
                                    <option multiple="true" value="{{ $task->id }}">{{ $task->title_en }}</option>
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
        var testSuperCategories = @json($test->categories[0]->supercategories);
        var testCategories = @json($test->categories);
        var testTasks = @json($test->tasks);
    </script>
@endpush
