package Requests;

import DataManagement.HeaderData;
import DataManagement.KeyUniqueId;

public interface IRequest extends KeyUniqueId {

    void addUDPToBuffer (HeaderData hd);

    void swapUniqueId();
}
