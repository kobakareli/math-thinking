@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Articles
                    <a class="pull-right" href="{{ url('/admin/articles/create/') }}">Add New</a>
                </div>

                <div class="panel-body">
                    @foreach ($articles as $article)
                        <div class="about-container" style="margin-bottom: 20px">
                            <span class="about-title">{{ $article->title_en }}</span>

                            <a class="delete pull-right" href="{{ url('/') . '/admin/articles/delete/' . $article->id }}">Delete</a>
                            <a class="update pull-right" style="margin-right: 10px" href="{{ url('/') . '/admin/articles/edit/' . $article->id }}">Edit</a>
                        </div>
                    @endforeach
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
