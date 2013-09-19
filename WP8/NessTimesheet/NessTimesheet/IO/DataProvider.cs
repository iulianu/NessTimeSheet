using NessTimesheet.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;
using Newtonsoft.Json;

namespace NessTimesheet.IO
{
    public class DataProvider
    {
        public async Task<AppModel> ReadFile()
        {
            // Get the local folder.
            StorageFolder local = Windows.Storage.ApplicationData.Current.LocalFolder;

            if (local != null)
            {
                // Get the DataFolder folder.
                var dataFolder = await local.GetFolderAsync("SampleData");

                // Get the file.
                var file = await dataFolder.GetFileAsync("DataFile.txt");

                // Read the data.
                using (StreamReader streamReader = new StreamReader(await file.OpenStreamForReadAsync()))
                {
                    return JsonConvert.DeserializeObject<AppModel>(streamReader.ReadToEnd());
                }

            }
            return null;
        }
    }
}
