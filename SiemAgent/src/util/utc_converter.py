import dateutil.parser
import pytz
from dateutil.tz import tzlocal


def local_to_utc(local_time_iso):
    local_time = dateutil.parser.parse(local_time_iso)
    local_time = local_time.replace(tzinfo=tzlocal())
    return local_time.astimezone(pytz.utc).isoformat()
