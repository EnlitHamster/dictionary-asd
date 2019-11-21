#ifndef __LIST_IMPL_H__
#define __LIST_IMPL_H__

#include "../List.h"
#include "../Iterator.h"

#define BASE_SIZE 8

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

// [summary] the function checks if the list is full; if it is, the list gets
// resized
// [parameters] list - list instance
static void _check_and_extend_list(list_t list);

#endif
