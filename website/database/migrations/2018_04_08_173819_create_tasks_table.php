<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTasksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tasks', function (Blueprint $table) {
            $table->increments('id');
            $table->timestamps();
            $table->text('title_en');
            $table->text('title_ge');
            $table->text('description_en');
            $table->text('description_ge');
            $table->text('hint_en')->nullable();
            $table->text('hint_ge')->nullable();
            $table->text('answer_en')->nullable();
            $table->text('answer_ge')->nullable();
            $table->text('numeric_answer');
            $table->integer('total_answers')->default(0);
            $table->integer('correct_answers')->default(0);
            $table->boolean('has_options');
            $table->text('option_1_en')->nullable();
            $table->text('option_1_ge')->nullable();
            $table->text('option_2_en')->nullable();
            $table->text('option_2_ge')->nullable();
            $table->text('option_3_en')->nullable();
            $table->text('option_3_ge')->nullable();
            $table->text('option_4_en')->nullable();
            $table->text('option_4_ge')->nullable();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('tasks');
    }
}
