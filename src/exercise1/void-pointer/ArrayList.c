#include "ArrayList.h"
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>

struct list_s
{
	void** array;
	size_t size;
	size_t maxSize;
};

struct iterator_s
{
	list_t list;
	size_t index;
};

iterator_t list_iterator(list_t list)
{
	iterator_t resultIterator = (iterator_t) malloc(sizeof(struct iterator_s));
	resultIterator->index = 0;
	resultIterator->list = list;
	return resultIterator;
}

bool is_iterator_valid(iterator_t iterator)
{
	return iterator->index < iterator->list->size;
}

void* iterator_get(iterator_t iterator)
{
	return iterator->list->array[iterator->index];
	++(iterator->index);
}

void iterator_next(iterator_t iterator)
{
	++(iterator->index);
}

void delete_iterator(iterator_t iterator)
{
	free(iterator);
}

list_t new_list()
{
	list_t resultList = (list_t) malloc(sizeof(struct list_s));
	resultList->size = 0;
	resultList->maxSize = BASE_SIZE;
	resultList->array = (void**) calloc(BASE_SIZE, sizeof(void*));
	return resultList;
}

void delete_list(list_t list)
{
	free(list->array);
	free(list);
}

size_t list_size(list_t list)
{
	return list->size;
}

bool is_list_empty(list_t list)
{
	return list->size == 0;
}

void* list_get(list_t list, size_t pos)
{
	if (pos >= 0 && pos < list->size)
	{
		return list->array[pos];
	}
	else
	{
		errno = EPERM;
		return NULL;
	}
}

void list_add(list_t list, void * elem)
{
	//check if the array has enough space in order to contain the new elements
	//if not it is extended
	_check_and_extend_list(list);
	list->array[list->size] = elem;
	(list->size)++;
}

bool list_pop(list_t list)
{
	if (list->size>0)
	{
		--(list->size);
		return true;
	}
	else
	{
		errno = EPERM;
		return false;
	}
}

bool list_insert(list_t list, void * elem, size_t pos)
{
	if (pos >= 0 && pos <= list->size)
	{
		//check if the array has enough space in order to contain the new elements
		//if not it is extended
		_check_and_extend_list(list);
		//move each element in the range [pos...list->size - 1] in his next position.
		for (size_t i = list->size; i > pos; --i)
		{
			list->array[i] = list->array[i - 1];
		}

		list->array[pos] = elem;
		++(list->size);
		return true;
	}
	else
	{
		errno = EPERM;
		return false;
	}
}

bool list_remove(list_t list, size_t pos){
	if (pos >= 0 && pos < list->size)
	{
		//move each element in the range [pos+1...list->size - 1] in his previous position.
		//by doing this the element pointed by pos is overrided.
		for (int i = pos; i < list->size - 1; ++i)
		{
			list->array[i] = list->array[i+1];
		}

		--(list->size);
		return true;
	}
	else
	{
		errno = EPERM;
		return false;
	}
}

static void _check_and_extend_list(list_t list)
{
	if (list->size >= list->maxSize)
	{
		list->array = (void**) realloc(list->array, 2*list->maxSize*sizeof(void*));
		list->maxSize *= 2;
	}
}
