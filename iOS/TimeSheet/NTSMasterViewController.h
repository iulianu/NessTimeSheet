//
//  NTSMasterViewController.h
//  TimeSheet
//
//  Created by Iulian Dogariu on 18/07/2013.
//  Copyright (c) 2013 Ness. All rights reserved.
//

#import <UIKit/UIKit.h>

@class NTSDayDetailViewController;

#import <CoreData/CoreData.h>

@interface NTSMasterViewController : UITableViewController <NSFetchedResultsControllerDelegate>

@property (strong, nonatomic) NTSDayDetailViewController *detailViewController;

@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (strong, nonatomic) NSManagedObjectContext *managedObjectContext;

+ (NSString*)formatDate:(NSDate*)date;

@end
