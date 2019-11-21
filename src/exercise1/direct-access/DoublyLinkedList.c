#include "DoublyLinkedList.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>

// -- Type implementations

struct list_s
{
	node_t head;
	node_t tail;
	size_t elemSize;
	size_t size;
};

struct iterator_s
{
	node_t node;
	size_t elemSize;
};

// -- Iterator.h implementations

bool is_iterator_valid(iterator_t iterator)
{
	return iterator->node != NULL;
}

void* iterator_get(iterator_t iterator)
{
	return _node_get(iterator->node, iterator->elemSize);
}

void iterator_next(iterator_t iterator)
{
	iterator->node = _next_node(iterator->node);
}

// -- List.h implementations

bool is_list_empty(list_t list)
{
	return list->size == 0;
}

void list_add(list_t list, void* elem)
{
	node_t new = _new_node(_NULL(), _NULL(), elem, list->elemSize);
	if (list->size != 0)
	{
		_set_node_previous(new, &(list->tail));
		_set_node_next(list->tail, &new);
		list->tail = new;
	}
	else
	{
		list->head = new;
		list->tail = new;
	}

	++(list->size);
}

bool list_insert(list_t list, void* elem, size_t pos)
{
	if (pos > list->size + 1)
	{
		errno = EPERM;
		return false;
	}

	if (pos == list->size)
	{
		list_add(list, elem);
		return true;
	}
	else if (pos == 0)
	{
		node_t node = list->head;
		node_t new = _new_node(_NULL(), &node, elem, list->elemSize);
		_set_node_previous(node, &new);
		list->head = new;
	}
	else
	{
		node_t node = list->head;
		for (size_t iPos = 0; iPos < pos; ++iPos)
		{
			node = _next_node(node);
		}

		node_t prev = _previous_node(node);
		node_t new = _new_node(&prev, &node, elem, list->elemSize);
		_set_node_next(prev, &new);
		_set_node_previous(node, &new);
	}

	++(list->size);
	return true;
}

bool list_pop(list_t list)
{
	if (is_list_empty(list))
	{
		errno = EPERM;
		return false;
	}

	if (list->size == 1)
	{
		_delete_node(list->tail);
		list->head = NULL;
		list->tail = NULL;
	}
	else
	{
		node_t prev = _previous_node(list->tail);
		_set_node_next(prev, _NULL());
		_delete_node(list->tail);
		list->tail = prev;
	}

	--(list->size);

	return true;
}

bool list_remove(list_t list, size_t pos)
{
	if (is_list_empty(list) || pos > list->size)
	{
		errno = EPERM;
		return false;
	}

	if (pos == list->size - 1)
	{
		return list_pop(list);
	}
	else if (pos == 0)
	{
		node_t node = list->head;
		node_t next = _next_node(node);
		_set_node_previous(next, _NULL());
		_delete_node(node);
		list->head = next;
	}
	else
	{
		node_t node = list->head;
		for (size_t iPos = 0; iPos < pos; ++iPos)
		{
			node = _next_node(node);
		}

		node_t prev = _previous_node(node);
		node_t next = _next_node(node);
		_set_node_next(prev, &next);
		_set_node_previous(next, &prev);
		_delete_node(node);
	}

	--(list->size);

	if (list->size == 0)
	{
		list->head = NULL;
		list->tail = NULL;
	}

	return true;
}

void* list_get(list_t list, size_t pos)
{
	if (pos > list->size)
	{
		fprintf(stderr, "list_get -> List index (%zu) out of bounds (0:%zu)\n", pos, list->size);
		exit(EXIT_FAILURE);
	}

	node_t node = list->head;
	for (size_t iPos = 0; iPos < pos; ++iPos)
	{
		node = _next_node(node);
	}

	return _node_get(node, list->elemSize);
}

size_t list_size(list_t list)
{
	return list->size;
}

iterator_t list_iterator(list_t list)
{
	iterator_t iter = (iterator_t) malloc(sizeof(struct iterator_s));
	iter->node = list->head;
	iter->elemSize = list->elemSize;
	return iter;
}

// -- ArrayList.h implementations

list_t new_list(size_t elemSize)
{
	list_t list = (list_t) malloc(sizeof(struct list_s));
	list->head = NULL;
	list->tail = NULL;
	list->size = 0;
	list->elemSize = elemSize;
	return list;
}

void delete_list(list_t list)
{
	node_t node = list->head;
	while (node && _next_node(node))
	{
		node_t tmp = _next_node(node);
		_delete_node(node);
		node = tmp;
	}

	free(list->tail);
	free(list);
}

void delete_iterator(iterator_t iterator)
{
	free(iterator);
}

// -- Utility functions

static node_t* _NULL()
{
	node_t* node = malloc(sizeof(node_t));
	*node = NULL;
	return node;
}

static node_t _previous_node(node_t node)
{
	size_t nodeSize = sizeof(node_t);
	node_t* prev = malloc(nodeSize);
	memcpy(prev, node, nodeSize);
	return *prev;
}

static node_t _next_node(node_t node)
{
	size_t nodeSize = sizeof(node_t);
	node_t* next = malloc(nodeSize);
	memcpy(next, node + nodeSize, nodeSize);
	return *next;
}

static void* _node_get(node_t node, size_t elemSize)
{
	size_t nodeSize = sizeof(node_t*);
	void* elem = malloc(sizeof(void*));
	memcpy(elem, node + 2 * nodeSize, elemSize);
	return elem;
}

static node_t _new_node(node_t* prev, node_t* next, void* elem, size_t elemSize)
{
	size_t nodeSize = sizeof(node_t);
	node_t new = malloc(2 * nodeSize + elemSize);
	memcpy(new, prev, nodeSize);
	memcpy(new + nodeSize, next, nodeSize);
	memcpy(new + 2 * nodeSize, elem, elemSize);
	return new;
}

static void _set_node_previous(node_t node, node_t* prev)
{
	memcpy(node, prev, sizeof(node_t));
}

static void _set_node_next(node_t node, node_t* next)
{
	size_t nodeSize = sizeof(node_t);
	memcpy(node + nodeSize, next, nodeSize);
}

static void _set_node_value(node_t node, void* elem, size_t elemSize)
{
	size_t nodeSize = sizeof(node_t);
	memcpy(node + 2 * nodeSize, elem, elemSize);
}

static void _delete_node(node_t node)
{
	free(node);
}
