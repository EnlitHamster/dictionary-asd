#ifndef __PREPROC_BOOSTER_H__
#define __PREPROC_BOOSTER_H__

// The preprocessor booster is used to define which implementation to be
// used to compile all the files using C lists as implemented in exercise 1

#define PPB_IDENT(X) X
#define PPB_XSTR(X) #X
#define PPB_STR(X) PPB_XSTR(X)
#define PPB_PATH(X,Y) PPB_STR(PPB_IDENT(X)PPB_IDENT(Y))

#ifdef DIRECT_ACCESS
#define PPB_DIR ../exercise1/direct-access/
#define PPB_NEW_LIST(elemSize) new_list(elemSize)
#else
#define PPB_DIR ../exercise1/void-pointer/
#define PPB_NEW_LIST(elemSize) new_list()
#endif

#ifdef ARRAY_LIST
#define PPB_FILE ArrayList.h
#else
#define PPB_FILE DoublyLinkedList.h
#endif

#endif
