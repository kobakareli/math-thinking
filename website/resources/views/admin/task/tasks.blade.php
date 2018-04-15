@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Tasks
                    <a class="pull-right" href="{{ url('/admin/tasks/create/') }}">Add New</a>
                </div>

                <div class="panel-body">
                    @foreach ($tasks as $task)
                        <div class="about-container" style="margin-bottom: 20px">
                            <span class="about-title">{{ $task->title_en }}</span>

                            <a class="delete pull-right" href="{{ url('/') . '/admin/tasks/delete/' . $task->id }}">Delete</a>
                            <a class="update pull-right" style="margin-right: 10px" href="{{ url('/') . '/admin/tasks/edit/' . $task->id }}">Edit</a>
                        </div>
                    @endforeach
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
