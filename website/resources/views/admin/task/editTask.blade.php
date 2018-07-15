@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Edit Task</div>

                <div class="panel-body">
                    <form method="POST" enctype="multipart/form-data" action="/admin/tasks/update/{{ $task->id }}">

                        {{ csrf_field() }}
                        {{ method_field('PATCH') }}

                        <input type="checkbox" name="has_options" class="about-title" style="margin-right: 5px; margin-bottom: 20px;" value="options" @if($task->has_options) {{ 'checked' }} @endif>Check if this task has optional answers</input>
                        <br>
                        <span class="language-marker">English:</span>
                        <div class="form-group">
                            <input name="title_en" class="form-control about-title" placeholder="English title" value="{{ $task->title_en }}"></input>
                            <span class="language-marker hide show">Description:</span>
                            <textarea id="rich1" name="description_en" class="hide show rich1 form-control about-text" placeholder="English description">{{ $task->description_en }}</textarea>
                            <span class="language-marker hide show">Hint:</span>
                            <textarea id="rich2" name="hint_en" class="hide show rich1 form-control about-text" placeholder="English hint">{{ $task->hint_en }}</textarea>
                            <span class="language-marker hide show">Solution:</span>
                            <textarea id="rich3" name="answer_en" class="hide show rich1 form-control about-text" placeholder="English answer">{{ $task->answer_en }}</textarea>
                            <input name="option_1_en" style="margin-top: 20px;" class="hide form-control about-title" placeholder="English optional answer 1" value="{{ $task->option_1_en }}"></input>
                            <input name="option_2_en" class="hide form-control about-title" placeholder="English optional answer 2" value="{{ $task->option_2_en }}"></input>
                            <input name="option_3_en" class="hide form-control about-title" placeholder="English optional answer 3" value="{{ $task->option_3_en }}"></input>
                            <input name="option_4_en" class="hide form-control about-title" placeholder="English optional answer 4" value="{{ $task->option_4_en }}"></input>
                        </div>

                        <span class="language-marker">Georgian:</span>
                        <div class="form-group">
                            <input name="title_ge" class="form-control about-title" placeholder="Georgian title" value="{{ $task->title_ge }}"></input>
                            <span class="language-marker hide show">Description:</span>
                            <textarea id="rich4" name="description_ge" class="hide show rich1 form-control about-text" placeholder="Georgian description">{{ $task->description_ge }}</textarea>
                            <span class="language-marker hide show">Hint:</span>
                            <textarea id="rich5" name="hint_ge" class="hide show rich1 form-control about-text" placeholder="Georgian hint">{{ $task->hint_ge }}</textarea>
                            <span class="language-marker hide show">Solution:</span>
                            <textarea id="rich6" name="answer_ge" class="hide show rich1 form-control about-text" placeholder="Georgian answer">{{ $task->answer_ge }}</textarea>
                            <input name="option_1_ge" style="margin-top: 20px;" class="hide form-control about-title" placeholder="Georgian optional answer 1" value="{{ $task->option_1_ge }}"></input>
                            <input name="option_2_ge" class="hide form-control about-title" placeholder="Georgian optional answer 2" value="{{ $task->option_2_ge }}"></input>
                            <input name="option_3_ge" class="hide form-control about-title" placeholder="Georgian optional answer 3" value="{{ $task->option_3_ge }}"></input>
                            <input name="option_4_ge" class="hide form-control about-title" placeholder="Georgian optional answer 4" value="{{ $task->option_4_ge }}"></input>
                        </div>

                        <span class="language-marker">Enter correct numeric answer. If this task has optional answers, enter correct option number.</span>
                        <input name="numeric_answer" class="numeric-answer form-control about-title" placeholder="Numeric answer" value="{{ $task->numeric_answer }}"></input>

                        <span class="language-marker">Select Super Category:</span>
                        <div class="form-group">
                            <select class="form-control" id="supercategories" name="supercategories[]" multiple>
                                @foreach($categories as $category)
                                    <option multiple="true" value="{{ $category->id }}">{{ $category->title_en }}</option>
                                @endforeach
                            </select>
                        </div>

                        <span class="language-marker">Select Category:</span>
                        <div class="form-group categories-wrapper">
                            <select class="form-control" id="categories" name="categories[]" multiple>
                                @foreach($subcategories as $category)
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
        var testSuperCategories = @json($task->categories[0]->supercategories);
        var testCategories = @json($task->categories);
    </script>
@endpush
