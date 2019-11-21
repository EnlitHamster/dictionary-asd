#ifndef __LIST_H__
#define __LIST_H__

// Default boolean type library (only from C99 standard)
#include <stdbool.h>
// Library containing type definitions
#include <stddef.h>

#include "Iterator.h"

// Opaque pointer to the list structure, that must be implemented in the
// source file of the implementation
typedef struct list_s *list_t;

// [summary] the function destroy a list
// [paramters] the list that has sto be destroyed
extern void destroy_list(list_t list);

// [summary] the function create a new empty list
// [paramters] comparator that must be used to compare two member of the list_t
// [return] a pointer to the created list
extern list_t  new_list();

// [summary] the function checks if the list contains if the list contains any
// element at all. This must be of constant complexity O(1).
// [parameters] list - list instance
// [return] true = the list is empty | false = otherwise
extern bool is_list_empty(list_t list);

// [summary] the function adds the element @ the tail of the list. This must be
// of constant complexity O(1).
// [parameters] list - list instance
//				elem - element to add
extern void list_add(list_t list, void* elem);

// [summary] the function inserts the element at a specific position of the list.
// This must be of linear complexity O(n).
// [parameters] list - list instance
//				elem - element to add
//				pos - position of the element
// [return] true = operation successful | false = error
extern bool list_insert(list_t list, void* elem, size_t pos);

// [summary] the function deletes the tailing element of the list. This must be
// of constant complexity O(1).
// [parameters] list - list instance
// [return] true = operation successful | false = error
extern bool list_pop(list_t list);

// [summary] the function deletes the element at a specific position of the
// list. This must be of linear complexity O(n).
// [parameters] list - list instance
//				pos - position of the element
// [return] true = operation successful | false = error
extern bool list_remove(list_t list, size_t pos);

// [summary] the function returns the element at a specific position of the
// list. This must be of linear complexity O(n).
// [parameters] list - list instance
//				pos - position of the element
// [return] pointer to the value
extern void* list_get(list_t list, size_t pos);

// [summary] the function returns the number of elements in the list. This must
// be of constant complexity O(1).
// [parameters] list - list instance
// [return] list size
extern size_t list_size(list_t list);

// [summary] the function returns an iterator over the elements of the list.
// This must be of constant complexity O(1).
// [paramters] list - list instance
// [return] iterator instante
extern iterator_t list_iterator(list_t list);

// [summary] the function destroy an iterator
// This must be of constant complexity O(1).
// [paramters] iterator - iterator instance
extern void destroy_iterator(iterator_t iterator);

#endif
