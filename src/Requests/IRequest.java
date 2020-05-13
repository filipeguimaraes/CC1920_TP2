package Requests;

import Headers.HeaderData;
import Headers.KeyUniqueId;

public interface IRequest extends KeyUniqueId {

    void addUDPToBuffer (HeaderData hd);

    void swapUniqueId();
}
