#ifndef __MERGE_H__
#define __MERGE_H__

#include "../PreprocBooster.h"
#include PPB_PATH(PPB_DIR,PPB_FILE)

// opaque pointer at a generic comparison Function
typedef int (*Compare)(void*, void*);

// [summary] The function generates a new list given two other lists. It uses
// a generic function to compare the elements of the two lists, making the
// generated one an ordered list.
// [parameters] newList - generated ordered list
//        list1 - first list to merge
//        list2 - second list to merge
//        compareElems - generic compare function
extern void merge(list_t* newList, list_t list1, list_t list2, Compare compareElems);

#endif
