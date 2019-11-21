#include "ArrayList.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>

// -- Type implementations

struct list_s
{
	void* arr;
	size_t elemSize;
	size_t used;
	size_t size;
};

struct iterator_s
{
	void* ptr;
	size_t elems;
	size_t elemSize;
};

// -- Iterator.h implementations

bool is_iterator_valid(iterator_t iterator)
{
	return iterator->elems > 0;
}

void* iterator_get(iterator_t iterator)
{
	void* elem = malloc(sizeof(void*));
	memcpy(elem, iterator->ptr, iterator->elemSize);
	return elem;
}

void iterator_next(iterator_t iterator)
{
	iterator->ptr += iterator->elemSize;
	--(iterator->elems);
}

// -- List.h implementations

bool is_list_empty(list_t list)
{
	return list->used == 0;
}

void list_add(list_t list, void* elem)
{
	_check_and_extend_list(list);
	void* destination = list->arr + list->used * list->elemSize;
	memcpy(destination, elem, list->elemSize);
	++(list->used);
}

bool list_insert(list_t list, void* elem, size_t pos)
{
	if (pos > list->used) {
		errno = EPERM;
		return false;
	}

	_check_and_extend_list(list);

	// moving the elements from the position in which to insert the element
	// back by one position
	for (size_t iPos = list->used - 1; iPos >= pos && iPos < list->used; --iPos)
	{
		void* source = list->arr + iPos * list->elemSize;
		void* destination = source + list->elemSize;
		memcpy(destination, source, list->elemSize);
	}

	void* destination = list->arr + pos * list->elemSize;
	memcpy(destination, elem, list->elemSize);
	++(list->used);

	return true;
}

bool list_pop(list_t list)
{
	if (is_list_empty(list))
	{
		errno = EPERM;
		return false;
	}

	--(list->used);
	return true;
}

bool list_remove(list_t list, size_t pos)
{
	if (is_list_empty(list) || pos > list->used)
	{
		errno = EPERM;
		return false;
	}

	// moving elements ahead until they "cover" the element in position
	// pos
	for (size_t iPos = pos; iPos < list->used; ++iPos)
	{
		void* destination = list->arr + iPos * list->elemSize;
		void* source = destination + list->elemSize;
		memcpy(destination, source, list->elemSize);
	}

	--(list->used);
	return true;
}

void* list_get(list_t list, size_t pos)
{
	// Checking if position pos exists in list
	if (is_list_empty(list) || pos > list->used)
	{
		errno = EPERM;
		return NULL;
	}

	void* elem = malloc(sizeof(void*));
	memcpy(elem, list->arr + pos * list->elemSize, list->elemSize);
	return elem;
}

size_t list_size(list_t list)
{
	return list->used;
}

iterator_t list_iterator(list_t list)
{
	iterator_t iter = (iterator_t) malloc(sizeof(struct iterator_s));
	iter->ptr = list->arr;
	iter->elems = list->used;
	iter->elemSize = list->elemSize;
	return iter;
}

// -- ArrayList.h implementations

list_t new_list(size_t elemSize)
{
	list_t result = (list_t) malloc(sizeof(struct list_s));
	result->arr = (void*) malloc(elemSize * BASE_SIZE);
	result->size = BASE_SIZE;
	result->used = 0;
	result->elemSize = elemSize;
	return result;
}

void delete_list(list_t list)
{
	free(list->arr);
	free(list);
}

void delete_iterator(iterator_t iterator)
{
	free(iterator);
}

// -- Utility functions

static void _check_and_extend_list(list_t list)
{
	if (list->used == list->size)
	{
		if (list->size == 0) {
			++(list->size);
		} else {
			list->size *= 2;
		}

		list->arr = realloc(list->arr, list->elemSize * list->size);
	}
}
