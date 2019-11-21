// Inside src/tests, run
// clear; gcc -o tests.out tests.c ../types/DoublyLinkedList.c unity.c && ./tests.out
// or run
// gcc -Wall -I ../types/ -c tests.c
// and use
// #include "DoublyLinkedList.h"
// instead of
// #include "../types/DoublyLinkedList.h"
// clear; gcc -o tests.out tests.c ../types/DoublyLinkedList.c ../../Resources/C/Unity/unity.c && ./tests.out

#include <stdlib.h>
#include <stdio.h>

#if (defined(ARRAY_LIST) || defined(DOUBLY_LINKED_LIST)) && (defined(DIRECT_ACCESS) || defined(VOID_POINTER))

#include "../PreprocBooster.h"
#include PPB_PATH(PPB_DIR,PPB_FILE)
#include "../../Resources/C/Unity/unity.h"
#include "../../Resources/C/Unity/unity_internals.h"

static int* _new_int(int val)
{
	int* integer = (int*) malloc(sizeof(int));
	*integer = val;

	return integer;
}

static list_t _build_list()
{
	list_t list = PPB_NEW_LIST(sizeof(int));
	list_add(list, _new_int(0));
	list_add(list, _new_int(1));
	list_add(list, _new_int(2));
	list_add(list, _new_int(3));

	return list;
}

static void _free_list(list_t list)
{
	delete_list(list);
}

static void test_is_list_empty()
{
	list_t list = PPB_NEW_LIST(sizeof(int));
	TEST_ASSERT_TRUE(is_list_empty(list));
	_free_list(list);
}

static void test_delete_list() {
	list_t list = PPB_NEW_LIST(sizeof(int));
	delete_list(list);
	TEST_ASSERT_EQUAL_INT(0, 0);
}

static void test_double_pop() {
	list_t list = _build_list();
	list_pop(list);
	list_pop(list);
	TEST_ASSERT_EQUAL_INT(2, list_size(list));
	_free_list(list);
}

static void test_pop_and_add() {
	list_t list = _build_list();
	list_pop(list);
	list_pop(list);
	list_pop(list);
	list_pop(list);
	list_add(list, _new_int(0));
	TEST_ASSERT_EQUAL_INT(1, list_size(list));
	_free_list(list);
}

static void test_list_add() {
	list_t list = PPB_NEW_LIST(sizeof(int));
	list_add(list, _new_int(8));
	TEST_ASSERT_EQUAL_INT(8, *((int*) list_get(list, 0)));
	_free_list(list);
}

static void test_insert_first() {
	list_t list = _build_list();
	list_insert(list, _new_int(8), 0);
	TEST_ASSERT_EQUAL_INT(8, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 2)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 3)));
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 4)));
	_free_list(list);
}

static void test_insert_last() {
	list_t list = _build_list();
	list_insert(list, _new_int(8), 4);
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 2)));
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 3)));
	TEST_ASSERT_EQUAL_INT(8, *((int*) list_get(list, 4)));
	_free_list(list);
}

static void test_list_insert() {
	list_t list = _build_list();
	list_insert(list, _new_int(8), 3);
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 2)));
	TEST_ASSERT_EQUAL_INT(8, *((int*) list_get(list, 3)));
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 4)));
	_free_list(list);
}

static void test_remove_first() {
	list_t list = _build_list();
	list_remove(list, 0);
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 2)));
	_free_list(list);
}

static void test_remove_last() {
	list_t list = _build_list();
	list_remove(list, 3);
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 2)));
	_free_list(list);
}

static void test_list_remove() {
	list_t list = _build_list();
	list_remove(list, 1);
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 2)));
	_free_list(list);
}

static void test_list_remove_size_one() {
	list_t list = PPB_NEW_LIST(sizeof(int));
	list_add(list, _new_int(5));
	list_remove(list, 0);
	TEST_ASSERT_EQUAL_INT(0, list_size(list));
	_free_list(list);
}

static void test_empty_list_size() {
	list_t list = PPB_NEW_LIST(sizeof(int));
	TEST_ASSERT_EQUAL_INT(0, list_size(list));
	_free_list(list);
}

static void test_list_size() {
	list_t list = _build_list();
	list_add(list, _new_int(8));
	TEST_ASSERT_EQUAL_INT(5, list_size(list));
	list_pop(list);
	TEST_ASSERT_EQUAL_INT(4, list_size(list));
	_free_list(list);
}

static void test_pop_single_element() {
	list_t list = PPB_NEW_LIST(sizeof(int));
	list_add(list, _new_int(8));
	list_pop(list);
	TEST_ASSERT_EQUAL_INT(0, list_size(list));
	_free_list(list);
}

static void test_list_get() {
	list_t list = _build_list();
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 0)));
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 1)));
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 2)));
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 3)));
	_free_list(list);
}

static void test_get_first_element() {
	list_t list = _build_list();
	TEST_ASSERT_EQUAL_INT(0, *((int*) list_get(list, 0)));
	_free_list(list);
}

static void test_get_second_element() {
	list_t list = _build_list();
	TEST_ASSERT_EQUAL_INT(1, *((int*) list_get(list, 1)));
	_free_list(list);
}

static void test_get_third_element() {
	list_t list = _build_list();
	TEST_ASSERT_EQUAL_INT(2, *((int*) list_get(list, 2)));
	_free_list(list);
}

static void test_get_last_element() {
	list_t list = _build_list();
	TEST_ASSERT_EQUAL_INT(3, *((int*) list_get(list, 3)));
	_free_list(list);
}

static void test_iterator_get() {
	list_t list = _build_list();
	iterator_t it = list_iterator(list);
	size_t i = 0;
	while (is_iterator_valid(it) && i < list_size(list))
	{
		TEST_ASSERT_EQUAL_INT(*(int*) iterator_get(it), *(int*) list_get(list, i));
		iterator_next(it);
		++i;
	}
	delete_iterator(it);
	_free_list(list);
}

int main(int argc, char* argv[]) {
	UNITY_BEGIN();
	RUN_TEST(test_is_list_empty);
	RUN_TEST(test_delete_list);
	RUN_TEST(test_double_pop);
	RUN_TEST(test_pop_and_add);
	RUN_TEST(test_list_add);
	RUN_TEST(test_insert_first);
	RUN_TEST(test_insert_last);
	RUN_TEST(test_list_insert);
	RUN_TEST(test_remove_first);
	RUN_TEST(test_remove_last);
	RUN_TEST(test_list_remove);
	RUN_TEST(test_list_remove_size_one);
	RUN_TEST(test_empty_list_size);
	RUN_TEST(test_list_size);
	RUN_TEST(test_pop_single_element);
	RUN_TEST(test_list_get);
	RUN_TEST(test_get_first_element);
	RUN_TEST(test_get_second_element);
	RUN_TEST(test_get_third_element);
	RUN_TEST(test_get_last_element);
	RUN_TEST(test_iterator_get);
	UNITY_END();

	exit(EXIT_SUCCESS);
}

#else

int main(void)
{
	fprintf(stderr, "No list implementation given!\n");

	exit(EXIT_FAILURE);
}

#endif
