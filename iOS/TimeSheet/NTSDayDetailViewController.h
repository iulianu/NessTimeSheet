//
//  NTSDetailViewController.h
//  TimeSheet
//
//  Created by Iulian Dogariu on 18/07/2013.
//  Copyright (c) 2013 Ness. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NTSDayDetailViewController : UITableViewController <UISplitViewControllerDelegate>

@property (strong, nonatomic) id detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
