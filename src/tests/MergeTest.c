#include <stdlib.h>
#include <stdio.h>

#if (defined(ARRAY_LIST) || defined(DOUBLY_LINKED_LIST)) && (defined(DIRECT_ACCESS) || defined(VOID_POINTER))

#include "../PreprocBooster.h"
#include PPB_PATH(PPB_DIR,PPB_FILE)
#include "../exercise1/Merge.h"
#include "../../Resources/C/Unity/unity.h"
#include "../../Resources/C/Unity/unity_internals.h"

int compare_int_ptr(void* elem1, void* elem2)
{
	if (*(int*) elem1 == *(int*) elem2)
	{
  	return 0;
	}

	if (*(int*)elem1 < *(int*)elem2)
	{
		return -1;
	}

	return 1;
}

static int* new_int(int value)
{
	int* integer = (int*) malloc(sizeof(int));
	*integer = value;
	return integer;
}

static void full_free(list_t list)
{
#ifdef VOID_POINTER
	iterator_t it = list_iterator(list);
	while (is_iterator_valid(it))
	{
		free(iterator_get(it));
		iterator_next(it);
	}

	delete_iterator(it);
#endif
	delete_list(list);
}

// This function create a list filled with n number.
// The first number is begin, and step is the difference between two continuos number in the list
static list_t build_list(int begin, int n, int step)
{
	list_t list = PPB_NEW_LIST(sizeof(int));
	int elem = begin;
	for (int i = 0; i < n; i++)
	{
		list_add(list, new_int(elem));
		elem += step;
	}
	return list;
}

static void test_empty_lists_merge()
{
	list_t a = PPB_NEW_LIST(sizeof(int));
	list_t b = PPB_NEW_LIST(sizeof(int));
	list_t result = PPB_NEW_LIST(sizeof(int));
	merge(&result, a, b, compare_int_ptr);
	TEST_ASSERT_EQUAL_INT(1, is_list_empty(result));
	TEST_ASSERT_EQUAL_INT(0, list_size(result));
	full_free(result);
}

static void test_one_empty_list_merge()
{
	list_t a = PPB_NEW_LIST(sizeof(int));
	list_t b = build_list(0, 3, 1);
	list_t result = PPB_NEW_LIST(sizeof(int));
	merge(&result, a, b, compare_int_ptr);
	for (int i = 0; i < list_size(result); i++)
	{
		TEST_ASSERT_EQUAL_INT(i, *(int*) list_get(result, i));
	}
	full_free(result);
}

static void test_contiguous_list_merge()
{
	list_t a = build_list(0, 3, 1);
	list_t b = build_list(3, 4, 1);
	list_t result = PPB_NEW_LIST(sizeof(int));
	merge(&result, a, b, compare_int_ptr);
	for (int i = 0; i < list_size(result); i++)
	{
		TEST_ASSERT_EQUAL_INT(i, *(int*) list_get(result, i));
	}
	full_free(result);
}

static void test_commutativity()
{
	list_t a = build_list(3, 3, 1);
	list_t b = build_list(0, 8, 1);
	list_t result1 = PPB_NEW_LIST(sizeof(int));
	merge(&result1, a, b, compare_int_ptr);
	list_t result2 = PPB_NEW_LIST(sizeof(int));
	merge(&result2, b, a, compare_int_ptr);
	TEST_ASSERT_EQUAL_INT(list_size(result1), list_size(result2));
	for (int i = 0; i < list_size(result1); i++)
	{
		TEST_ASSERT_EQUAL_INT(*(int*) list_get(result1, i), *(int*) list_get(result2, i));
	}
	full_free(result2);
}

static void test_equal_lists_merge()
{
	list_t a = build_list(0, 3, 1);
	list_t b = build_list(0, 3, 1);
	list_t result = PPB_NEW_LIST(sizeof(int));
	merge(&result, a, b, compare_int_ptr);
	TEST_ASSERT_EQUAL_INT(6, list_size(result));
	TEST_ASSERT_EQUAL_INT(0, *(int*)list_get(result, 0));
	TEST_ASSERT_EQUAL_INT(0, *(int*)list_get(result, 1));
	TEST_ASSERT_EQUAL_INT(1, *(int*)list_get(result, 2));
	TEST_ASSERT_EQUAL_INT(1, *(int*)list_get(result, 3));
	TEST_ASSERT_EQUAL_INT(2, *(int*)list_get(result, 4));
	TEST_ASSERT_EQUAL_INT(2, *(int*)list_get(result, 5));
	full_free(result);
}

static void test_odd_even_lists_merge()
{
	list_t a = build_list(0, 3, 2); // 0 2 4
	list_t b = build_list(1, 3, 2); // 1 3 5
	list_t result = PPB_NEW_LIST(sizeof(int));
	merge(&result, a, b, compare_int_ptr);
	for (int i = 0; i < list_size(result); i++)
	{
		TEST_ASSERT_EQUAL_INT(i, *(int*)list_get(result, i));
	}
	full_free(result);
}

/*
N.B: In each of the testing method you have to choose between making a full_free
		 of both a and b, or only the result list. Infact the pointer in the result list
		 are the same of the one in the a and b, so if you make all the full_free then it happens
		 that you make two times the free of the same pointer
*/

int main() {
	UNITY_BEGIN();
	RUN_TEST(test_empty_lists_merge);
	RUN_TEST(test_one_empty_list_merge);
	RUN_TEST(test_contiguous_list_merge);
	RUN_TEST(test_commutativity);
	RUN_TEST(test_equal_lists_merge);
	RUN_TEST(test_odd_even_lists_merge);
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
