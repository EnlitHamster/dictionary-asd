#ifndef __ITERATOR_H__
#define __ITERATOR_H__

// Default boolean type library (only from C99 standard)
#include <stdbool.h>

// Opaque pointer to the iterator structure, that must be implemented in the
// source file of the implementation
typedef struct iterator_s *iterator_t;

// [summary] the function checks if the iterator is still in a valid range or
// if it has exhausted the elements it was cycling. This must be of constant
// complexity O(1).
// [parameters] iterator - iterator instance
// [return] true = valid iterator | false = otherwise
extern bool is_iterator_valid(iterator_t iterator);

// [summary] the function returns the value of the current element the iterator
// is on. This must be of constant complexity O(1).
// [parameters] iterator - iterator instance
// [return] pointer to the value
extern void* iterator_get(iterator_t iterator);

// [summary] the function moves the iterator to its next position. This must be
// of constant complexity O(1).
// [paramters] iterator - iterator instance
extern void iterator_next(iterator_t iterator);

// [summary] the function destroy the given iterator
// This must be of constant complexity O(1).
// [parameters] it - the instance of the iteraor
extern void destroy_iterator(iterator_t it);

#endif
