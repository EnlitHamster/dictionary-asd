#ifndef __LIST_IMPL_H__
#define __LIST_IMPL_H__

#include "../List.h"
#include "../Iterator.h"

typedef void* node_t;

// [summary] the function creates an empty list based on the size of a single
// element. This makes the list occupy less memory (removing the middle-man
// pointer) but it limits the variability of the elements (unless the element
// is a void* at which point this behaves the same as the pointer access
// array list)
// [parameters] elemSize - size of an element
// [return] empty list
extern list_t new_list(size_t elemSize);

// [summary] the function deletes the entire list
// [parameters] list - list instance
extern void delete_list(list_t list);

// [summary] the function delets the iterator
// [paramters] iterator - iterator instance
extern void delete_iterator(iterator_t iterator);

// [summary] generates a NULL pointing node
// [return] null node
static node_t* _NULL();

// [summary] access scheme to the node memory area of the previous node pointer
// [parameters] node - memory area
// [return] previous node
static node_t _previous_node(node_t node);

// [summary] access scheme to the node memory area of the next node pointer
// [parameters] node - memory area
// [return] next node
static node_t _next_node(node_t node);

// [summary] access scheme to the node memory area of the value
// [parameters] node - memory area
//				elemSize - size of the element
// [return] pointer to value
static void* _node_get(node_t node, size_t elemSize);

// [summary] creates a memory area containing the node data and initializes its
// values
// [parameters] prev - pointer to previous node
//				next - pointer to next node
//				elem - value to store
//				elemSize - size of the element
// [return] new node
static node_t _new_node(node_t* prev, node_t* next, void* elem, size_t elemSize);

// [summary] changes the previous node of a given node
// [parameters] node - memory area
//				previous - previous node
static void _set_node_previous(node_t node, node_t* prev);

// [summary] changes the next node of a given node
// [parameters] node - memory area
//				next - next node
static void _set_node_next(node_t node, node_t* next);

// [summary] sets the value contained in the node
// [parameters] node - memory area
//				elem - value to store
//				elemSize - size of the element
static void _set_node_value(node_t node, void* elem, size_t elemSize);

// [summary] freeing the node memory area
// [parameters] node - memory area
static void _delete_node(node_t node);

#endif
