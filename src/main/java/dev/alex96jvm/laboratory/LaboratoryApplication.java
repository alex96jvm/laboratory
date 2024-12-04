package dev.alex96jvm.laboratory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Спринг бут + data jpa (градл вместо мавена).
 * Кеш 2-го уровня - редис с прогревом кеша батчами при старте.
 * Профайлинг аспект считает сколько времени выполнялся каждый метод и сколько раз (выводит в консоль ежеминутно).
 * Джоб шедулер ежедневно проверяет выполнение домашки лаборантом - если false, то отнимает один балл рейтинга.
 */

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@EnableScheduling
public class LaboratoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(LaboratoryApplication.class, args);
	}
}
