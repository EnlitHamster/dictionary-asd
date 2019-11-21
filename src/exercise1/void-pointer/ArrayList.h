#ifndef __LIST_IMPL_H__
#define __LIST_IMPL_H__

#include "../List.h"
#include "../Iterator.h"

#define BASE_SIZE 8

// [summary] the function creates an empty list with a base list size.
// [return] empty list
extern list_t new_list();

// [summary] the function deletes the entire list
// [parameters] list - list instance
extern void delete_list(list_t list);

// [summary] the function delets the iterator
// [paramters] iterator - iterator instance
extern void delete_iterator(iterator_t iterator);

// [summary] the function checks if the list is full; if it is, the list gets
// resized
// [parameters] list - list instance
static void _check_and_extend_list(list_t list);

#endif
