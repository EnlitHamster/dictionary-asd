#include "DoublyLinkedList.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>

// -- Type implementations

struct node_s
{
	struct node_s* prev;
	void* val;
	struct node_s* next;
};

struct list_s
{
	node_t start;
	node_t end;
	size_t size;
};

struct iterator_s
{
	node_t elem;
};

// -- Iterator.h implementations

bool is_iterator_valid(iterator_t iterator)
{
  return iterator->elem != NULL;
}

void* iterator_get(iterator_t iterator)
{
	if (iterator->elem != NULL)
	{
		return iterator->elem->val;
	}
	else
	{
		return NULL;
	}
}

void iterator_next(iterator_t iterator)
{
	if (iterator->elem != NULL)
	{
		iterator->elem = iterator->elem->next;
	}
}

// -- List.h implementations

list_t new_list()
{
	list_t list = malloc(sizeof(struct list_s));

	if (list == NULL)
	{
		errno = EPERM;
	}
	else
	{
		list->start = NULL;
		list->end = NULL;
		list->size = 0;
	}

	return list;
}

void delete_list(list_t list)
{
  while (list->start != NULL)
	{
  	node_t tmp = list->start;
  	list->start = tmp->next;
  	free(tmp);
  }

	free(list);
}

bool is_list_empty(list_t list)
{
  return (list == NULL || list->size == 0);
}

void list_add(list_t list, void* elem)
{
  node_t node = _new_node(list->end, elem, NULL);

  if (list->end != NULL)
	{
  	list->end->next = node;
  }

	if (list->start == NULL)
	{
		list->start = node;
	}

	list->end = node;
	list->size = list->size + 1;
}

bool list_insert(list_t list, void* elem, size_t pos)
{
	if (pos == 0)
	{
		node_t node = _new_node(NULL, elem, list->start);

		if (list->start != NULL)
		{
		  list->start->prev = node;
		}

		list->start = node;
		list->size = list->size + 1;
	}
	else if (pos == list->size)
	{
		list_add(list, elem);
	}
	else if (pos > 0 && pos < list->size)
	{
		node_t tmp = list->start;
		int i = 0;
		while (tmp != NULL && i < pos)
    {
			tmp = tmp->next;
			i++;
		}

		if (tmp != NULL)
    {
			node_t node = _new_node(tmp->prev, elem, tmp);
			(tmp->prev)->next = node;
			tmp->prev = node;
		}

		list->size = list->size + 1;
	}
	else
	{
		errno = EPERM;
		return false;
	}

	return true;
}

bool list_pop(list_t list)
{
	if (list->size == 0)
	{
		errno = EPERM;
		return false;
	}

	node_t tmp = list->end->prev;
	free(list->end);
	list->end = tmp;

	if (list->end != NULL)
	{
		list->end->next = NULL;
	}

	if (list->size == 1)
	{
		list->start = NULL;
	}

	list->size = list->size - 1;

	return true;
}

bool list_remove(list_t list, size_t pos)
{
	if (pos < 0 || pos >= list->size || list->size == 0)
	{
		errno = EPERM;
		return false;
	}

	if (list->size == 1 || pos == list->size - 1)
	{
		list_pop(list);
	}
	else
	{
		if (pos == 0)
		{
			node_t tmp = list->start;
			list->start = list->start->next;
			list->start->prev = NULL;
			free(tmp);
		}
		else
		{
			node_t tmp = list->start;
			size_t i = 0;

			while (tmp != NULL && i < pos - 1)
			{
				++i;
				tmp = tmp->next;
			}

			node_t to_delete = tmp->next;
			tmp->next = tmp->next->next;
			tmp->next->prev = tmp;
			free(to_delete);
		}

		list->size = list->size - 1;
	}

	return true;
}

void* list_get(list_t list, size_t pos)
{
	if (pos < 0 || pos >= list->size)
	{
		errno = EPERM;
		return NULL;
	}

	node_t tmp = list->start;
	unsigned int i = 0;
	bool found = false;

	while (tmp != NULL && i != pos)
	{
		++i;
		tmp = tmp->next;
	}

	return tmp->val;
}

size_t list_size(list_t list)
{
	if (list != NULL)
	{
		return list->size;
	}
	else
	{
		return 0;
	}
}

iterator_t list_iterator(list_t list)
{
	iterator_t iterator = malloc(sizeof(struct iterator_s));

	if (iterator == NULL)
	{
		errno = EPERM;
		return NULL;
	}

	iterator->elem = list->start;

	return iterator;
}

void delete_iterator(iterator_t iterator)
{
	free(iterator);
}

// -- Utility functions

static node_t _new_node(node_t prev, void* elem, node_t next)
{
	node_t node = malloc(sizeof(struct node_s));

	if (node == NULL)
	{
		errno = EPERM;
		return NULL;
	}

 	node->prev = prev;
  	node->val = elem;
  	node->next = next;

  	return node;
}
