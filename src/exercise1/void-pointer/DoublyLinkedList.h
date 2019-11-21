#ifndef __LIST_IMPL_H__
#define __LIST_IMPL_H__

#include "../List.h"
#include "../Iterator.h"

typedef struct node_s* node_t;

// [summary] the function creates an empty list with a base list size.
// [return] empty list
extern list_t new_list();

// [summary] the function deletes the entire list
// [parameters] list - list instance
extern void delete_list(list_t list);

// [summary] the function delets the iterator
// [paramters] iterator - iterator instance
extern void delete_iterator(iterator_t iterator);

// [summary] creates the new node's structure
// [parameters] prev - pointer to previous node
//				elem - value to store
//				next - pointer to next node
// [return] new node
static node_t _new_node(node_t prev, void* elem, node_t next);

#endif
